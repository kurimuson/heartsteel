package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.model.PlayTime

actual class MixerPlayerService {

    actual fun isInitialled(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getIsPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getCurrentPlayTime(): PlayTime {
        TODO("Not yet implemented")
    }

    actual fun getIsControlEnable(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun lockControl() {
    }

    actual fun unlockControl() {
    }

    actual fun updateStep(step: Int, onFinish: () -> Unit) {
    }

    actual fun updateTrackList(trackList: List<String>, onFinish: () -> Unit) {
    }

    actual fun play(onFinish: () -> Unit) {
    }

    actual fun pause(onFinish: () -> Unit) {
    }

    actual fun stop(onFinish: () -> Unit) {
    }

    actual fun turnTo(millis: Int, onFinish: () -> Unit) {
    }

    actual fun addPlayStateCallback(callback: (Boolean) -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removePlayStateCallback(uid: String) {
    }

    actual fun addPlayTimeCallback(callback: (PlayTime) -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removePlayTimeCallback(uid: String) {
    }

    actual fun addControlEnableCallback(callback: (Boolean) -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removeControlEnableCallback(uid: String) {
    }

    actual fun addLoadingStateCallback(callback: (Boolean, String) -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removeLoadingStateCallback(uid: String) {
    }

    actual fun addOnAutoStopCallback(callback: () -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removeOnAutoStopCallback(uid: String) {
    }

    actual fun getCurrentVoiceHigh(): FloatArray {
        TODO("Not yet implemented")
    }

    actual fun release(onFinish: () -> Unit) {
    }

    actual fun destroy(onFinish: () -> Unit) {
    }

}