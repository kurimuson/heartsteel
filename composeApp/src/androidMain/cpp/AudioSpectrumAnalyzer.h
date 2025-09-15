#ifndef AUDIO_SPECTRUM_ANALYZER_H
#define AUDIO_SPECTRUM_ANALYZER_H

#include <vector>
#include <atomic>
#include <mutex>
#include <cstdint>

namespace oboe_engine {

    class AudioSpectrumAnalyzer {

    public:
        // 构造函数：需要采样率和声道数初始化
        AudioSpectrumAnalyzer(int32_t sampleRate, int32_t channels);

        ~AudioSpectrumAnalyzer() = default;

        // 处理音频样本（从Oboe的outputBuffer传入）
        void processAudioSamples(const int16_t *sampleData, int32_t numFrames);

        // 获取当前计算好的频谱数据（线程安全）
        std::vector<uint16_t> getCurrentVoiceHigh();

        // 开启/关闭频谱分析
        void openAnalyse();

        void closeAnalyse();

        // 检查是否正在分析
        bool isAnalysing() const { return mIsAnalysing.load(); }

    private:
        // 配置参数
        static constexpr int kSampleBlockSize = 1024; // 分析块大小（类似fftSize）

        // 核心数据
        std::vector<int16_t> mSampleBuffer;           // 单声道样本缓冲区
        std::vector<uint16_t> mVoiceHigh;              // 频谱数据（0~255）
        std::atomic<bool> mIsAnalysing{false};     // 分析开关（默认关闭）
        int32_t mBufferIndex = 0;                     // 缓冲区当前索引
        int32_t mSampleRate;                          // 采样率（用于频率计算）
        int32_t mChannels;                            // 声道数（用于转单声道）

        // 横向平滑
        int mMergeCount = 4;                  // 合并相邻bin的数量（控制参数）
        const int kMinMergeCount = 1;         // 最小合并数量（1=不合并）
        const int kMaxMergeCount = 6;         // 最大合并数量（值越大横向越平滑）

        // 纵向平滑
        std::vector<uint16_t> mPrevVoiceHigh;  // 上一帧的结果
        std::mutex mPrevVoiceMutex;            // 保护历史数据的锁
        const float mSmoothFactor = 0.25f;      // 平滑系数（0~1，越小越平滑）

        // 线程安全锁
        std::mutex mVoiceHighMutex;

        // 内部方法：计算频谱
        void computeSpectrum();

        // 内部方法：多声道转单声道
        int16_t convertToMono(const int16_t *frameData);
    };

}  // namespace oboe_engine

#endif