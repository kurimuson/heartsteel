package com.crimson.app.heartsteel.model

import kotlinx.serialization.Serializable

@Serializable
data class AppearanceState(
    internal var darkModeFollowBySystem: Boolean,
    internal var darkMode: Boolean,
    internal var theme: ThemeEnum,
    internal var roundWatch: Boolean,
    internal var showMusicVerna: Boolean,
    internal var showMusicEffect: Boolean,
    internal var musicEffectColor: MusicEffectColorEnum,
)
