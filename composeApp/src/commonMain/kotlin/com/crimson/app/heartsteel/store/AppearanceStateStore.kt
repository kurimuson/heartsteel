package com.crimson.app.heartsteel.store

import com.crimson.app.heartsteel.common.json
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.model.AppearanceState
import com.crimson.app.heartsteel.model.MusicEffectColorEnum

object AppearanceStateStore {

    internal val supportDarkMode: Boolean = Instance.appearanceService.supportDarkMode()
    internal var value = AppearanceState(
        darkModeFollowBySystem = false,
        darkMode = false,
        theme = ThemeEnum.Normal,
        roundWatch = true,
        showMusicVerna = true,
        showMusicEffect = true,
        musicEffectColor = MusicEffectColorEnum.Default
    )
    private var stateChangeCallback: ((AppearanceState) -> Unit)? = null
    private var roundWatchChangeCallback: ((Boolean) -> Unit)? = null
    private var showMusicVernaChangeCallback: ((Boolean) -> Unit)? = null
    private var showMusicEffectChangeCallback: ((Boolean) -> Unit)? = null
    private var musicEffectColorChangeCallback: ((MusicEffectColorEnum) -> Unit)? = null
    internal var currentAppIsInDarkMode = false
    internal var voiceHigh = FloatArray(0)

    fun loadState(onResult: (AppearanceState) -> Unit = {}) {
        Instance.storageService.loadData("appearance_state") { result ->
            try {
                val data = json.decodeFromString<AppearanceState>(result)
                if (supportDarkMode) {
                    value.darkModeFollowBySystem = data.darkModeFollowBySystem
                } else {
                    value.darkModeFollowBySystem = false
                }
                value.darkMode = data.darkMode
                value.theme = data.theme
                value.roundWatch = data.roundWatch
                value.showMusicVerna = data.showMusicVerna
                value.showMusicEffect = data.showMusicEffect
                value.musicEffectColor = data.musicEffectColor
                onResult(value)
            } catch (e: Exception) {
                Instance.storageService.saveData(
                    "appearance_state",
                    json.encodeToString(value)
                )
                onResult(value)
            }
        }
    }

    private fun saveState() {
        Instance.storageService.saveData("appearance_state", json.encodeToString(value))
    }

    fun addStateChangeCallback(callback: (AppearanceState) -> Unit) {
        stateChangeCallback = callback
    }

    fun removeStateChangeCallback() {
        stateChangeCallback = null
    }

    fun addRoundWatchChangeCallback(callback: (Boolean) -> Unit) {
        roundWatchChangeCallback = callback
    }

    fun removeRoundWatchChangeCallback() {
        roundWatchChangeCallback = null
    }

    fun addShowMusicVernaChangeCallback(callback: (Boolean) -> Unit) {
        showMusicVernaChangeCallback = callback
    }

    fun removeShowMusicVernaChangeCallback() {
        showMusicVernaChangeCallback = null
    }

    fun addShowMusicEffectChangeCallback(callback: (Boolean) -> Unit) {
        showMusicEffectChangeCallback = callback
    }

    fun removeShowMusicEffectChangeCallback() {
        showMusicEffectChangeCallback = null
    }

    fun addMusicEffectColorChangeCallback(callback: (MusicEffectColorEnum) -> Unit) {
        musicEffectColorChangeCallback = callback
    }

    fun removeMusicEffectColorChangeCallback() {
        musicEffectColorChangeCallback = null
    }

    fun setDarkModeFollowBySystem(v: Boolean) {
        value.darkModeFollowBySystem = v
        saveState()
        stateChangeCallback?.invoke(value)
    }

    fun setDarkMode(v: Boolean) {
        value.darkMode = v
        saveState()
        stateChangeCallback?.invoke(value)
    }

    fun setTheme(v: ThemeEnum) {
        value.theme = v
        saveState()
        stateChangeCallback?.invoke(value)
    }

    fun setRoundWatch(v: Boolean) {
        value.roundWatch = v
        saveState()
        roundWatchChangeCallback?.invoke(isRoundWatch())
    }

    fun setShowMusicVerna(v: Boolean) {
        value.showMusicVerna = v
        saveState()
        showMusicVernaChangeCallback?.invoke(isShowMusicVerna())
    }

    fun setShowMusicEffect(v: Boolean) {
        value.showMusicEffect = v
        saveState()
        showMusicEffectChangeCallback?.invoke(isShowMusicEffect())
    }

    fun setMusicEffectColor(v: MusicEffectColorEnum) {
        value.musicEffectColor = v
        saveState()
        musicEffectColorChangeCallback?.invoke(v)
    }

    fun isRoundWatch(): Boolean {
        return Instance.appearanceService.isWatch() && value.roundWatch
    }

    fun isShowMusicVerna(): Boolean {
        return !Instance.appearanceService.isWatch() && value.showMusicVerna
    }

    fun isShowMusicEffect(): Boolean {
        return !Instance.appearanceService.isWatch() && value.showMusicEffect
    }

}