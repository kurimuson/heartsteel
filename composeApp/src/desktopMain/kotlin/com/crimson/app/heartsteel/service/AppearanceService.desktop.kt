package com.crimson.app.heartsteel.service

actual class AppearanceService {

    actual fun statusBarToDark(flag: Boolean) {
    }

    actual fun supportDarkMode(): Boolean {
        return false
    }

    actual fun isDarkMode(): Boolean {
        return false
    }

    actual fun addDarkModeChangeCallback(callback: (Boolean) -> Unit) {
    }

    actual fun removeDarkModeChangeCallback() {
    }

    actual fun isWatch(): Boolean {
        return false
    }

    actual fun isForeground(): Boolean {
        TODO("Not yet implemented")
    }

}