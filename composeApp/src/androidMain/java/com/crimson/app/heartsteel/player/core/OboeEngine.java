package com.crimson.app.heartsteel.player.core;

import android.content.res.AssetManager;

public class OboeEngine {

    public interface OboeErrorCallback {
        void onError(String errorMessage);
    }

    private static final int AUDIO_SAMPLE_RATE = 48000; // 48000Hz
    private static final int AUDIO_CHANNELS = 2; // 2声道
    private static final int AUDIO_DEPTH = 16; // 16bit

    private final long engine;

    static {
        System.loadLibrary("oboe_engine");
    }

    public OboeEngine(AssetManager assetMgr, String filePath, OboeErrorCallback errorCallback) {
        this.engine = this.nativeCreate(assetMgr, filePath, AUDIO_SAMPLE_RATE, AUDIO_CHANNELS, AUDIO_DEPTH, errorCallback);
        this.openVoiceHighAnalyse();
    }

    public void play() {
        this.nativePlay(this.engine);
    }

    public void pause() {
        this.nativePause(this.engine);
    }

    public void stop() {
        this.nativeStop(this.engine);
    }

    public void turnTo(long milliseconds) {
        this.nativeTurnTo(this.engine, milliseconds);
    }

    public long getCurrentPosition() {
        return this.nativeGetCurrentPosition(this.engine);
    }

    public void setVolume(float volume) {
        this.nativeSetVolume(this.engine, volume);
    }

    public void openVoiceHighAnalyse() {
        nativeOpenVoiceHighAnalyse(this.engine);
    }

    public void closeVoiceHighAnalyse() {
        nativeCloseVoiceHighAnalyse(this.engine);
    }

    public short[] getCurrentVoiceHigh() {
        return nativeGetCurrentVoiceHigh(this.engine);
    }

    public void destroy() {
        this.closeVoiceHighAnalyse();
        this.nativeDestroy(this.engine);
    }

    // 以下为 native bridge

    private native long nativeCreate(AssetManager assetMgr, String filePath, int sampleRate, int audioChannel, int audioFormat, OboeErrorCallback errorCallback);

    private native void nativePlay(long engineHandle);

    private native void nativePause(long engineHandle);

    private native void nativeStop(long engineHandle);

    private native void nativeTurnTo(long engineHandle, long milliseconds);

    private native void nativeSetVolume(long engineHandle, float volume);

    private native long nativeGetCurrentPosition(long engineHandle);

    private native void nativeOpenVoiceHighAnalyse(long engineHandle);

    private native void nativeCloseVoiceHighAnalyse(long engineHandle);

    private native short[] nativeGetCurrentVoiceHigh(long engineHandle);

    private native void nativeDestroy(long engineHandle);

}
