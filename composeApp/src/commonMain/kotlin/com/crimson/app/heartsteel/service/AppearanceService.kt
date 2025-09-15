package com.crimson.app.heartsteel.service

expect class AppearanceService {

    constructor()

    fun statusBarToDark(flag: Boolean)

    fun supportDarkMode(): Boolean

    fun isDarkMode(): Boolean

    fun addDarkModeChangeCallback(callback: (Boolean) -> Unit)

    fun removeDarkModeChangeCallback()

    fun isWatch(): Boolean

    fun isForeground(): Boolean

}
