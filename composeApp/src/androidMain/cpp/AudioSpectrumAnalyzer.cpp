#include "AudioSpectrumAnalyzer.h"
#include <cmath>
#include <algorithm>
#include "kissfft/kiss_fftr.h"
#include "logging_macros.h"

namespace oboe_engine {

    AudioSpectrumAnalyzer::AudioSpectrumAnalyzer(int32_t sampleRate, int32_t channels)
            : mSampleRate(sampleRate), mChannels(channels) {
        mSampleBuffer.resize(kSampleBlockSize);
        mVoiceHigh.resize(kSampleBlockSize, 0);
    }

    void AudioSpectrumAnalyzer::processAudioSamples(const int16_t *sampleData, int32_t numFrames) {
        // 如果未开启分析，直接返回
        if (!mIsAnalysing.load()) return;
        // 处理每帧数据，转换为单声道并填充缓冲区
        for (int i = 0; i < numFrames; i++) {
            // 计算当前帧在样本数据中的起始索引
            const int16_t *frameStart = sampleData + (i * mChannels);
            // 转换为单声道样本
            int16_t monoSample = convertToMono(frameStart);
            // 存入缓冲区
            mSampleBuffer[mBufferIndex++] = monoSample;
            // 当缓冲区满时计算频谱
            if (mBufferIndex >= kSampleBlockSize) {
                computeSpectrum();
                mBufferIndex = 0;
            }
        }
    }

    std::vector<uint16_t> AudioSpectrumAnalyzer::getCurrentVoiceHigh() {
        std::lock_guard<std::mutex> lock(mVoiceHighMutex);
        return mVoiceHigh; // 返回副本，避免外部修改内部数据
    }

    void AudioSpectrumAnalyzer::openAnalyse() {
        mIsAnalysing = true;
        // 重置缓冲区，避免残留数据
        mBufferIndex = 0;
        std::fill(mSampleBuffer.begin(), mSampleBuffer.end(), 0);
    }

    void AudioSpectrumAnalyzer::closeAnalyse() {
        mIsAnalysing = false;
    }

    // 同js的fftSize快速傅里叶变换
    void AudioSpectrumAnalyzer::computeSpectrum() {
        // FFT结果通常取一半（对称），后续会进一步合并相邻bin
        const int fftSize = kSampleBlockSize;
        const int rawBinCount = fftSize / 2 + 1;
        std::vector<uint16_t> newVoiceHigh;

        // 1. 准备时域样本（应用汉宁窗）
        std::vector<float> fftInput(fftSize);
        for (int i = 0; i < fftSize; i++) {
            float sample = static_cast<float>(mSampleBuffer[i]) / 32767.0f;
            float hanning = 0.5f * (1.0f - cos(2.0f * M_PI * i / (fftSize - 1)));
            fftInput[i] = sample * hanning;
        }

        // 2. 执行FFT
        kiss_fftr_cfg fftCfg = kiss_fftr_alloc(fftSize, 0, nullptr, nullptr);
        std::vector<kiss_fft_cpx> fftOutput(rawBinCount);
        kiss_fftr(fftCfg, fftInput.data(), fftOutput.data());

        // 3. 计算原始幅度
        std::vector<float> rawMagnitudes(rawBinCount);
        for (int i = 0; i < rawBinCount; i++) {
            float real = fftOutput[i].r;
            float imag = fftOutput[i].i;
            rawMagnitudes[i] = std::sqrt(real * real + imag * imag);
        }

        // 4. 横向平滑：使用控制参数mMergeCount合并相邻bin
        int actualMergeCount = std::clamp(mMergeCount, kMinMergeCount, kMaxMergeCount);
        const int smoothedBinCount = rawBinCount / actualMergeCount;
        newVoiceHigh.resize(smoothedBinCount, 0);

        for (int i = 0; i < smoothedBinCount; i++) {
            float sum = 0.0f;
            int start = i * actualMergeCount;
            int end = std::min(start + actualMergeCount, rawBinCount);

            for (int j = start; j < end; j++) {
                sum += rawMagnitudes[j];
            }

            float avgMagnitude = sum / (end - start);

            // 对数缩放归一化
            float logScaled = log1p(avgMagnitude * 500.0f);
            float normalized = std::clamp(logScaled * 30.0f, 0.0f, 255.0f);
            newVoiceHigh[i] = static_cast<uint16_t>(normalized);
        }

        // 5. 纵向平滑（帧间融合）
        std::vector<uint16_t> finalVoiceHigh;
        {
            std::lock_guard<std::mutex> lock(mPrevVoiceMutex);
            if (mPrevVoiceHigh.empty() || mPrevVoiceHigh.size() != newVoiceHigh.size()) {
                finalVoiceHigh = newVoiceHigh;
            } else {
                finalVoiceHigh.resize(newVoiceHigh.size());
                for (int i = 0; i < newVoiceHigh.size(); i++) {
                    float blended = newVoiceHigh[i] * mSmoothFactor +
                                    mPrevVoiceHigh[i] * (1.0f - mSmoothFactor);
                    finalVoiceHigh[i] = static_cast<uint16_t>(std::round(blended));
                }
            }
            mPrevVoiceHigh = finalVoiceHigh;
        }

        // 6. 释放资源并更新结果
        free(fftCfg);
        std::lock_guard<std::mutex> lock(mVoiceHighMutex);
        mVoiceHigh.swap(finalVoiceHigh);
    }

    int16_t AudioSpectrumAnalyzer::convertToMono(const int16_t *frameData) {
        // 多声道取平均值转换为单声道
        int32_t sum = 0;
        for (int c = 0; c < mChannels; c++) {
            sum += frameData[c];
        }
        return static_cast<int16_t>(sum / mChannels);
    }

}  // namespace oboe_engine
