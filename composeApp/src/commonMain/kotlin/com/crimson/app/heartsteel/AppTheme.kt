package com.crimson.app.heartsteel

import androidx.compose.material3.ColorScheme
import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.model.AppearanceState
import com.crimson.app.heartsteel.theme.blue.blueDarkScheme
import com.crimson.app.heartsteel.theme.blue.blueLightScheme
import com.crimson.app.heartsteel.theme.green.greenDarkScheme
import com.crimson.app.heartsteel.theme.green.greenLightScheme
import com.crimson.app.heartsteel.theme.normal.normalDarkScheme
import com.crimson.app.heartsteel.theme.normal.normalLightScheme
import com.crimson.app.heartsteel.theme.red.redDarkScheme
import com.crimson.app.heartsteel.theme.red.redLightScheme
import com.crimson.app.heartsteel.theme.yellow.yellowDarkScheme
import com.crimson.app.heartsteel.theme.yellow.yellowLightScheme

val getColorScheme: (Boolean, AppearanceState) -> ColorScheme = { isDarkMode, themeState ->
    if (isDarkMode) {
        when (themeState.theme) {
            ThemeEnum.Normal -> normalDarkScheme
            ThemeEnum.Green -> greenDarkScheme
            ThemeEnum.Red -> redDarkScheme
            ThemeEnum.Yellow -> yellowDarkScheme
            ThemeEnum.Blue -> blueDarkScheme
        }
    } else {
        when (themeState.theme) {
            ThemeEnum.Normal -> normalLightScheme
            ThemeEnum.Green -> greenLightScheme
            ThemeEnum.Red -> redLightScheme
            ThemeEnum.Yellow -> yellowLightScheme
            ThemeEnum.Blue -> blueLightScheme
        }
    }
}
