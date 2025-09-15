#ifndef OBOE_ENGINE_H
#define OBOE_ENGINE_H

#include <oboe/AudioStream.h>
#include <oboe/AudioStreamBuilder.h>
#include <oboe/AudioStreamCallback.h>
#include <vector>
#include <atomic>
#include <queue>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <android/asset_manager.h>
#include <jni.h>
#include "logging_macros.h"
#include "AudioSpectrumAnalyzer.h"

namespace oboe_engine {

    class OboeEngine : public oboe::AudioStreamCallback {
    public:
        OboeEngine(AAssetManager *assetManager,
                   const char *filePath,
                   int32_t sampleRate, int32_t channels, int32_t bitDepth,
                   JavaVM *javaVM, jobject errorCallback);

        ~OboeEngine() override;

        bool start();

        void pause();

        void stop();

        bool setPositionMs(int64_t targetMs);

        int64_t getCurrentPositionMs() const;

        void setVolume(float volume);

        void openVoiceHighAnalyse();

        void closeVoiceHighAnalyse();

        std::vector<uint16_t> getCurrentVoiceHigh();

        // Oboe回调
        oboe::DataCallbackResult onAudioReady(oboe::AudioStream *stream,
                                              void *audioData,
                                              int32_t numFrames) override;

        void onErrorAfterClose(oboe::AudioStream *stream, oboe::Result result) override;

    private:

        oboe::AudioStream *mStream = nullptr;
        oboe::AudioStreamBuilder mStreamBuilder;
        std::vector<int16_t> mAudioData;

        int32_t mSampleRate = 48000;
        int32_t mChannels = 2;
        int32_t mBitDepth = 16;
        int64_t mTotalFrames = 0;
        std::atomic<int64_t> mCurrentFrame = 0;

        std::unique_ptr<AudioSpectrumAnalyzer> mSpectrumAnalyzer; // 频谱分析器实例，分析器会自动通过unique_ptr释放

        // 线程安全的样本队列（存储待分析的音频帧）
        std::queue<std::vector<int16_t>> mSampleQueue;
        std::mutex mQueueMutex;
        std::condition_variable mQueueCV;
        std::atomic<bool> mIsProcessing{false}; // 工作线程是否运行
        std::thread mWorkerThread; // 工作线程

        JavaVM *mJavaVM = nullptr; // JVM指针
        jobject mErrorCallback = nullptr; // Java回调接口的全局引用

        bool loadAudioFromAsset(AAssetManager *assetManager, const char *filePath);

        void createStream();

        void callJavaErrorCallback(const char* errorMessage);

        enum class PlayState {
            Stopped, Playing, Paused
        };

        // 原子类型保证线程安全
        std::atomic<PlayState> mPlayState = PlayState::Stopped;
        std::atomic<float> mVolume{1.0f};

    };

}  // namespace oboe_engine

#endif
