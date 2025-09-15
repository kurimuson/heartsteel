package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.ContextHolder
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.player.OboeMixerPlayer

actual class MixerPlayerService {

    private var player = OboeMixerPlayer(ContextHolder.getContext())

    actual fun isInitialled(): Boolean {
        return player.isInitialled
    }

    actual fun getIsPlaying(): Boolean {
        return player.isPlaying
    }

    actual fun getCurrentPlayTime(): PlayTime {
        return player.currentPlayTime
    }

    actual fun getIsControlEnable(): Boolean {
        return player.isControlEnable
    }

    actual fun lockControl() {
        player.lockControl()
    }

    actual fun unlockControl() {
        player.unlockControl()
    }

    actual fun updateStep(step: Int, onFinish: () -> Unit) {
        player.updateStep(step, onFinish)
    }

    actual fun updateTrackList(trackList: List<String>, onFinish: () -> Unit) {
        player.updateTrackList(trackList, onFinish)
    }

    actual fun play(onFinish: () -> Unit) {
        player.play(onFinish)
    }

    actual fun pause(onFinish: () -> Unit) {
        player.pause(onFinish)
    }

    actual fun stop(onFinish: () -> Unit) {
        player.stop(onFinish)
    }

    actual fun turnTo(millis: Int, onFinish: () -> Unit) {
        player.turnTo(millis, onFinish)
    }

    actual fun addPlayStateCallback(callback: (Boolean) -> Unit): String {
        return player.addPlayStateCallback(callback)
    }

    actual fun removePlayStateCallback(uid: String) {
        player.removePlayStateCallback(uid)
    }

    actual fun addPlayTimeCallback(callback: (PlayTime) -> Unit): String {
        return player.addPlayTimeCallback(callback)
    }

    actual fun removePlayTimeCallback(uid: String) {
        player.removePlayTimeCallback(uid)
    }

    actual fun addControlEnableCallback(callback: (Boolean) -> Unit): String {
        return player.addControlEnableCallback(callback)
    }

    actual fun removeControlEnableCallback(uid: String) {
        return player.removeControlEnableCallback(uid)
    }

    actual fun addLoadingStateCallback(callback: (Boolean, String) -> Unit): String {
        return ""
    }

    actual fun removeLoadingStateCallback(uid: String) {
    }

    actual fun addOnAutoStopCallback(callback: () -> Unit): String {
        return player.addOnAutoStopCallback(callback)
    }

    actual fun removeOnAutoStopCallback(uid: String) {
        player.removeOnAutoStopCallback(uid)
    }

    actual fun getCurrentVoiceHigh(): FloatArray {
        return player.currentVoiceHigh
    }

    actual fun release(onFinish: () -> Unit) {
        player.release(onFinish)
    }

    actual fun destroy(onFinish: () -> Unit) {
        player.destroy(onFinish)
    }

}
