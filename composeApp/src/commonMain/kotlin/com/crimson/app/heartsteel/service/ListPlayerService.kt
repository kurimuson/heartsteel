package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitSave

expect class ListPlayerService {

    constructor()

    fun isInitialled(): Boolean

    fun getIsPlaying(): Boolean

    fun getCurrentPlayUid(): String

    fun getCurrentPlayTime(): PlayTime

    fun getIsControlEnable(): Boolean

    fun lockControl()

    fun unlockControl()

    fun updatePlayList(list: List<TraitSave>)

    fun listPlay(startAtUid: String, onFinish: () -> Unit = { })

    fun playPre(onFinish: () -> Unit = { })

    fun playNext(onFinish: () -> Unit = { })

    fun pause(onFinish: () -> Unit = { })

    fun stop(onFinish: () -> Unit = { })

    fun turnTo(millis: Int, onFinish: () -> Unit = { })

    fun addPlayStateCallback(callback: (Boolean) -> Unit): String

    fun removePlayStateCallback(uid: String)

    fun addPlayTimeCallback(callback: (PlayTime) -> Unit): String

    fun removePlayTimeCallback(uid: String)

    fun addControlEnableCallback(callback: (Boolean) -> Unit): String

    fun removeControlEnableCallback(uid: String)

    fun addLoadingStateCallback(callback: (Boolean, String) -> Unit): String

    fun removeLoadingStateCallback(uid: String)

    fun addOnAutoStopCallback(callback: () -> Unit): String

    fun removeOnAutoStopCallback(uid: String)

    fun addPlayUidUpdateCallback(callback: (String) -> Unit): String

    fun removePlayUidUpdateCallback(uid: String)

    fun getCurrentVoiceHigh(): FloatArray

    fun release(onFinish: () -> Unit = { })

    fun destroy(onFinish: () -> Unit = { })

}
