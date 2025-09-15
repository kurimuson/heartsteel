package com.crimson.app.heartsteel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.AppearanceState
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.store.ListPlayerStateStore
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import com.crimson.app.heartsteel.theme.AppTypography
import com.crimson.app.heartsteel.theme.normal.normalLightScheme
import com.crimson.app.heartsteel.ui.MainPanel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun App() {

    val scope = rememberCoroutineScope()

    var visible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(true) }
    var colorScheme by remember { mutableStateOf(normalLightScheme) }
    var roundWatch by remember { mutableStateOf(AppearanceStateStore.isRoundWatch()) }

    val updateAppTheme = { systemIsDarkMode: Boolean, themeState: AppearanceState ->
        if (themeState.darkModeFollowBySystem) {
            // 深色模式：跟随系统
            Instance.appearanceService.statusBarToDark(systemIsDarkMode)
            colorScheme = getColorScheme(systemIsDarkMode, themeState)
            AppearanceStateStore.currentAppIsInDarkMode = systemIsDarkMode
        } else {
            // 深色模式：自定义
            Instance.appearanceService.statusBarToDark(themeState.darkMode)
            colorScheme = getColorScheme(themeState.darkMode, themeState)
            AppearanceStateStore.currentAppIsInDarkMode = themeState.darkMode
        }
    }

    val darkModeChangeCallback = { isDarkMode: Boolean ->
        updateAppTheme(isDarkMode, AppearanceStateStore.value)
    }

    val loadStateFinish = { result: AppearanceState ->
        updateAppTheme(Instance.appearanceService.isDarkMode(), result)
        if (AppearanceStateStore.supportDarkMode) {
            Instance.appearanceService.addDarkModeChangeCallback(darkModeChangeCallback)
        }
        visible = true
    }

    val stateChangeCallback = { state: AppearanceState ->
        updateAppTheme(Instance.appearanceService.isDarkMode(), state)
    }

    val roundWatchChangeCallback = { value: Boolean ->
        if (roundWatch != value) {
            roundWatch = value
            scope.launch {
                delay(100)
                contentVisible = false
                delay(100)
                contentVisible = true
            }
        }
    }

    DisposableEffect(Unit) {
        AppearanceStateStore.loadState(loadStateFinish)
        AppearanceStateStore.addStateChangeCallback(stateChangeCallback)
        if (Instance.appearanceService.isWatch()) {
            AppearanceStateStore.addRoundWatchChangeCallback(roundWatchChangeCallback)
        }
        ListPlayerStateStore.loadTraitSaveList()
        MixerPlayerStateStore.addPlayerEventListener()
        ListPlayerStateStore.addPlayerEventListener()
        onDispose {
            if (AppearanceStateStore.supportDarkMode) {
                Instance.appearanceService.removeDarkModeChangeCallback()
            }
            if (Instance.appearanceService.isWatch()) {
                AppearanceStateStore.removeRoundWatchChangeCallback()
            }
            AppearanceStateStore.removeStateChangeCallback()
            MixerPlayerStateStore.removePlayerEventListener()
            ListPlayerStateStore.removePlayerEventListener()
        }
    }

    if (visible) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when {
                    contentVisible -> MainPanel()
                }
            }
        }
    }

}