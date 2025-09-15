package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitSave

actual class ListPlayerService {

    actual fun isInitialled(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getIsPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getCurrentPlayUid(): String {
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

    actual fun updatePlayList(list: List<TraitSave>) {
    }

    actual fun listPlay(startAtUid: String, onFinish: () -> Unit) {
    }

    actual fun playPre(onFinish: () -> Unit) {
    }

    actual fun playNext(onFinish: () -> Unit) {
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

    actual fun addPlayUidUpdateCallback(callback: (String) -> Unit): String {
        TODO("Not yet implemented")
    }

    actual fun removePlayUidUpdateCallback(uid: String) {
    }

    actual fun getCurrentVoiceHigh(): FloatArray {
        TODO("Not yet implemented")
    }

    actual fun release(onFinish: () -> Unit) {
    }

    actual fun destroy(onFinish: () -> Unit) {
    }

}