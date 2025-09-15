package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.appearance.AppearanceManager

actual class AppearanceService {

    private var manager = AppearanceManager()

    actual fun statusBarToDark(flag: Boolean) {
        manager.statusBarToDark(flag)
    }

    actual fun supportDarkMode(): Boolean {
        return manager.supportDarkMode()
    }

    actual fun isDarkMode(): Boolean {
        return manager.isDarkMode
    }

    actual fun addDarkModeChangeCallback(callback: (Boolean) -> Unit) {
        manager.addDarkModeChangeCallback(callback)
    }

    actual fun removeDarkModeChangeCallback() {
        manager.removeDarkModeChangeCallback()
    }

    actual fun isWatch(): Boolean {
        return manager.isWatch
    }

    actual fun isForeground(): Boolean {
        return manager.isForeground
    }

}