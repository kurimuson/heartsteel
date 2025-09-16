#include "OboeEngine.h"
#include <cstring>

namespace oboe_engine {

    OboeEngine::OboeEngine(AAssetManager *assetManager, const char *filePath,
                           int32_t sampleRate, int32_t channels, int32_t bitDepth,
                           JavaVM *javaVM, jobject errorCallback)
            : mSampleRate(sampleRate), mChannels(channels), mBitDepth(bitDepth),
              mJavaVM(javaVM), mErrorCallback(errorCallback) {
        // 1.加载音频文件
        if (!loadAudioFromAsset(assetManager, filePath)) {
            LOGE("Failed to load audio file");
            return;
        }
        // 2.创建流
        createStream();
        // 3. 初始化频谱分析器
        mSpectrumAnalyzer = std::make_unique<AudioSpectrumAnalyzer>(sampleRate, channels);
    }

    OboeEngine::~OboeEngine() {
        if (mStream) {
            mStream->stop();
            mStream->close();
            delete mStream;  // 手动释放stream
            mStream = nullptr;
        }
        mAudioData.clear();
        // 释放Java回调的全局引用
        if (mErrorCallback != nullptr && mJavaVM != nullptr) {
            JNIEnv *env = nullptr;
            jint envResult = mJavaVM->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6);
            if (envResult == JNI_EDETACHED) {
                // 线程未附着到JVM，临时附着
                if (mJavaVM->AttachCurrentThread(&env, nullptr) == JNI_OK) {
                    env->DeleteGlobalRef(mErrorCallback);
                    mJavaVM->DetachCurrentThread();
                }
            } else if (envResult == JNI_OK) {
                env->DeleteGlobalRef(mErrorCallback);
            }
            mErrorCallback = nullptr;
        }
        mJavaVM = nullptr;
        // 分析器会自动通过unique_ptr释放
    }

    bool OboeEngine::loadAudioFromAsset(AAssetManager *assetManager, const char *filePath) {
        AAsset *asset = AAssetManager_open(assetManager, filePath, AASSET_MODE_BUFFER);
        if (!asset) {
            return false;
        } else {
            off_t fileSize = AAsset_getLength(asset);
            const int16_t *assetData = reinterpret_cast<const int16_t *>(AAsset_getBuffer(asset));
            if (!assetData) {
                AAsset_close(asset);
                return false;
            }
            mAudioData.assign(assetData, assetData + (fileSize / sizeof(int16_t)));
            mTotalFrames = mAudioData.size() / mChannels;
            AAsset_close(asset);
            return true;
        }
    }

    void OboeEngine::createStream() {
        // 先销毁可能存在的旧流
        if (mStream) {
            mStream->stop();
            mStream->close();
            delete mStream;
            mStream = nullptr;
        }

        // 确定音频格式
        oboe::AudioFormat audioFormat;
        switch (mBitDepth) {
            case 16:
                audioFormat = oboe::AudioFormat::I16;
                break;
            case 24:
                audioFormat = oboe::AudioFormat::I24;
                break;
            case 32:
                audioFormat = oboe::AudioFormat::I32;
                break;
            default:
                LOGE("Unsupported bit depth: %d (only 16/24/32 are supported)", mBitDepth);
                audioFormat = oboe::AudioFormat::Invalid;
        }

        // 构建流
        auto result = mStreamBuilder
                .setCallback(this)
                ->setDirection(oboe::Direction::Output)
                ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
                ->setSharingMode(oboe::SharingMode::Exclusive)
                ->setUsage(oboe::Usage::Game)
                ->setSampleRate(mSampleRate)
                ->setChannelCount(mChannels)
                ->setFormat(audioFormat)
                ->setAudioApi(oboe::AudioApi::AAudio) // 已经在minSDK指定Android8了，所以这里直接使用AAudio
                ->openStream(&mStream);
        if (result != oboe::Result::OK || !mStream) {
            LOGE("Failed to open stream: %s", oboe::convertToText(result));
            mStream = nullptr;
        }

    }

    bool OboeEngine::start() {
        if (!mStream || mAudioData.empty()) return false;
        // 无论之前状态如何，都重新启动流（恢复回调）
        auto result = mStream->requestStart();
        if (result != oboe::Result::OK) {
            mPlayState = PlayState::Stopped;
            LOGE("start failed: %s", oboe::convertToText(result));
            return false;
        } else {
//            // 尝试重新创建一次流
//            createStream();
//            result = mStream->requestStart();
//            if (result != oboe::Result::OK) {
//                mPlayState = PlayState::Stopped;
//                LOGE("start failed: %s", oboe::convertToText(result));
//                return false;
//            }
        }
        // 启动成功后更新状态（保证状态同步）
        mPlayState = PlayState::Playing;
        return true;
    }

    void OboeEngine::pause() {
        if (!mStream || mPlayState != PlayState::Playing) return;
        if (mStream->requestPause() == oboe::Result::OK) {
            mPlayState = PlayState::Paused;
        }
    }

    void OboeEngine::stop() {
        if (!mStream || mPlayState == PlayState::Stopped) return;
        if (mStream->requestStop() == oboe::Result::OK) {
            mPlayState = PlayState::Stopped;
            mCurrentFrame = 0;
        }
    }

    int64_t OboeEngine::getCurrentPositionMs() const {
        if (mSampleRate == 0) return 0;
        return (mCurrentFrame * 1000) / mSampleRate;
    }

    void OboeEngine::setVolume(float volume) {
        mVolume = std::max(0.0f, std::min(volume, 1.0f));
    }

    void OboeEngine::openVoiceHighAnalyse() {
        if (mSpectrumAnalyzer) {
            mSpectrumAnalyzer->openAnalyse();
            mIsProcessing = true;
            // 启动工作线程
            mWorkerThread = std::thread([this]() {
                while (mIsProcessing) {
                    std::vector<int16_t> samples;
                    // 从队列中取数据（阻塞等待，直到有数据或停止）
                    {
                        std::unique_lock<std::mutex> lock(mQueueMutex);
                        mQueueCV.wait(lock, [this]() {
                            return !mSampleQueue.empty() || !mIsProcessing;
                        });
                        if (!mIsProcessing) break; // 停止信号，退出线程
                        samples = std::move(mSampleQueue.front());
                        mSampleQueue.pop();
                    }
                    // 处理数据（调用分析器）
                    mSpectrumAnalyzer->processAudioSamples(samples.data(),
                                                           samples.size() / mChannels);
                }
            });
        }
    }

    void OboeEngine::closeVoiceHighAnalyse() {
        if (mSpectrumAnalyzer) {
            mSpectrumAnalyzer->closeAnalyse();
            mIsProcessing = false;
            mQueueCV.notify_one(); // 唤醒线程，使其退出
            if (mWorkerThread.joinable()) {
                mWorkerThread.join(); // 等待线程结束
            }
            // 清空队列
            {
                std::lock_guard<std::mutex> lock(mQueueMutex);
                while (!mSampleQueue.empty()) {
                    mSampleQueue.pop();
                }
            }
        }
    }

    std::vector<uint16_t> OboeEngine::getCurrentVoiceHigh() {
        if (mSpectrumAnalyzer) {
            return mSpectrumAnalyzer->getCurrentVoiceHigh();
        }
        return {};
    }

    bool OboeEngine::setPositionMs(int64_t targetMs) {
        // 基础校验：流未初始化或无音频数据，直接返回失败
        if (!mStream || mAudioData.empty()) {
            LOGE("setPositionMs failed: stream not ready or no audio data");
            return false;
        }

        // -------------------------- 关键步骤1：暂停流，停止回调触发 --------------------------
        // 保存跳转前的原始状态（用于后续恢复，避免破坏原有播放状态）
        PlayState originalState = mPlayState.load();
        // 只有当前是“播放中”，才需要暂停（避免重复暂停）
        if (originalState == PlayState::Playing) {
            oboe::Result pauseResult = mStream->requestPause();
            if (pauseResult != oboe::Result::OK) {
                LOGE("setPositionMs failed to pause stream: %s", oboe::convertToText(pauseResult));
                return false;
            }
            mPlayState = PlayState::Paused; // 同步更新状态标记
        }

        // -------------------------- 关键步骤2：安全处理跳转逻辑 --------------------------
        // 计算目标帧（时间 → 帧数：ms * 采样率 / 1000）
        int64_t targetFrame = (targetMs * mSampleRate) / 1000;
        bool isBeyondTotal = (targetFrame >= mTotalFrames);

        if (isBeyondTotal) {
            // 情况1：目标超出总时长 → 停止播放 + 定位到末尾
            stop(); // 此处是“非回调线程”，调用stop()安全
            mCurrentFrame = mTotalFrames; // 确保位置标记到末尾（而非重置为0）
            LOGE("setPositionMs: target(%lldms) beyond total duration, stop playback",
                 static_cast<long long>(targetMs));
        } else {
            // 情况2：目标在有效范围内 → 限制到 [0, 总帧数)，设置位置
            targetFrame = std::max(int64_t(0), targetFrame); // 避免负数
            mCurrentFrame = targetFrame;
            LOGD("setPositionMs: jump to %lldms (frame: %lld)", static_cast<long long>(targetMs),
                 static_cast<long long>(targetFrame));

            // -------------------------- 关键步骤3：恢复原始播放状态 --------------------------
            // 如果跳转前是“播放中”，则重新启动流（恢复回调触发）
            if (originalState == PlayState::Playing) {
                oboe::Result startResult = mStream->requestStart();
                if (startResult != oboe::Result::OK) {
                    LOGE("setPositionMs failed to resume stream: %s",
                         oboe::convertToText(startResult));
                    mPlayState = PlayState::Stopped; // 恢复失败，设为停止状态
                    return false;
                }
                mPlayState = PlayState::Playing; // 同步更新状态标记
            }
            // 如果跳转前是“暂停”，则保持暂停（不恢复）
        }

        return true;
    }

    oboe::DataCallbackResult OboeEngine::onAudioReady(oboe::AudioStream *stream,
                                                      void *audioData,
                                                      int32_t numFrames) {
        // 非播放状态或无音频数据：直接填充静默
        if (mPlayState != PlayState::Playing || mAudioData.empty()) {
            memset(audioData, 0, numFrames * mChannels * sizeof(int16_t));
            return oboe::DataCallbackResult::Continue;
        }

        float currentVolume = mVolume.load();
        int16_t *outputBuffer = reinterpret_cast<int16_t *>(audioData);

        // 音量为0时的处理
        if (currentVolume <= 0.0f) {
            size_t bufferSize = numFrames * mChannels * sizeof(int16_t);
            memset(audioData, 0, bufferSize);

            int64_t framesToAdvance = std::min(static_cast<int64_t>(numFrames),
                                               mTotalFrames - mCurrentFrame);
            mCurrentFrame += framesToAdvance;

            if (mCurrentFrame >= mTotalFrames) {
                mPlayState = PlayState::Stopped;
                mCurrentFrame = 0;
                return oboe::DataCallbackResult::Stop;
            }

            if (mSpectrumAnalyzer && mSpectrumAnalyzer->isAnalysing()) {
                // 复制当前outputBuffer的数据到临时向量
                std::vector<int16_t> currentSamples(
                        outputBuffer,
                        outputBuffer + numFrames * mChannels
                );
                // 推入队列
                {
                    std::lock_guard<std::mutex> lock(mQueueMutex);
                    mSampleQueue.push(std::move(currentSamples));
                }
                mQueueCV.notify_one(); // 唤醒工作线程
            }
            return oboe::DataCallbackResult::Continue;
        }

        // 音量不为0时的处理
        int32_t framesWritten = 0;
        while (framesWritten < numFrames) {
            int32_t remainingFrames = static_cast<int32_t>(mTotalFrames - mCurrentFrame);
            if (remainingFrames <= 0) {
                memset(outputBuffer + (framesWritten * mChannels), 0,
                       (numFrames - framesWritten) * mChannels * sizeof(int16_t));
                mPlayState = PlayState::Stopped;
                mCurrentFrame = 0;
                return oboe::DataCallbackResult::Stop;
            }

            int32_t framesToCopy = std::min(numFrames - framesWritten, remainingFrames);
            const int16_t *sourceStart = mAudioData.data() + (mCurrentFrame * mChannels);

            // 复制并应用音量
            for (int i = 0; i < framesToCopy * mChannels; ++i) {
                int32_t scaledSample = static_cast<int32_t>(sourceStart[i] * currentVolume);
                scaledSample = std::clamp(scaledSample, static_cast<int32_t>(INT16_MIN),
                                          static_cast<int32_t>(INT16_MAX));
                outputBuffer[framesWritten * mChannels + i] = static_cast<int16_t>(scaledSample);
            }

            framesWritten += framesToCopy;
            mCurrentFrame += framesToCopy;
        }

        if (mSpectrumAnalyzer && mSpectrumAnalyzer->isAnalysing()) {
            // 复制当前outputBuffer的数据到临时向量
            std::vector<int16_t> currentSamples(
                    outputBuffer,
                    outputBuffer + numFrames * mChannels
            );
            // 推入队列
            {
                std::lock_guard<std::mutex> lock(mQueueMutex);
                mSampleQueue.push(std::move(currentSamples));
            }
            mQueueCV.notify_one(); // 唤醒工作线程
        }

        return oboe::DataCallbackResult::Continue;
    }

    void OboeEngine::onErrorAfterClose(oboe::AudioStream *stream, oboe::Result result) {
        const char *errorMsg = oboe::convertToText(result);
        LOGE("Stream error: %s", errorMsg);
        mPlayState = PlayState::Stopped;
        // 回调到Java
        callJavaErrorCallback(errorMsg);
    }

    void OboeEngine::callJavaErrorCallback(const char *errorMessage) {
        if (mJavaVM == nullptr || mErrorCallback == nullptr) {
            return; // 无回调对象，直接返回
        }
        JNIEnv *env = nullptr;
        jint envResult = mJavaVM->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6);
        bool isAttached = false;
        // 确保当前线程附着到JVM
        if (envResult == JNI_EDETACHED) {
            if (mJavaVM->AttachCurrentThread(&env, nullptr) != JNI_OK) {
                LOGE("Failed to attach thread for error callback");
                return;
            }
            isAttached = true;
        } else if (envResult != JNI_OK) {
            LOGE("Failed to get JNI environment");
            return;
        }
        // 查找回调接口的类和方法
        jclass callbackClass = env->GetObjectClass(mErrorCallback);
        if (callbackClass == nullptr) {
            LOGE("Failed to get callback class");
            if (isAttached) {
                mJavaVM->DetachCurrentThread();
            }
            return;
        }
        jmethodID onErrorMethod = env->GetMethodID(callbackClass, "onError",
                                                   "(Ljava/lang/String;)V");
        if (onErrorMethod == nullptr) {
            LOGE("Failed to find onError method");
            env->DeleteLocalRef(callbackClass);
            if (isAttached) {
                mJavaVM->DetachCurrentThread();
            }
            return;
        }
        // 调用Java回调方法
        jstring errorJstr = env->NewStringUTF(errorMessage);
        env->CallVoidMethod(mErrorCallback, onErrorMethod, errorJstr);
        // 清理局部引用
        env->DeleteLocalRef(errorJstr);
        env->DeleteLocalRef(callbackClass);
        // 若临时附着线程，需要 detach
        if (isAttached) {
            mJavaVM->DetachCurrentThread();
        }
    }

}  // namespace oboe_engine
