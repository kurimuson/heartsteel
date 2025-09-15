package com.crimson.app.heartsteel.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.page.detail.DetailPagePanel
import com.crimson.app.heartsteel.ui.page.home.HomePagePanel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainPanel() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home/mixer"
    ) {
        composable(route = "home/{tab}") { backStackEntry ->
            val tab = backStackEntry.savedStateHandle.get<String>("tab")
            HomePagePanel(
                navController = navController,
                tab = tab!!,
            )
        }
        composable(
            route = "detail/{name}",
//            enterTransition = {
//                fadeIn(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
//                ) + slideIntoContainer(
//                    animationSpec = tween(300, easing = EaseIn),
//                    towards = AnimatedContentTransitionScope.SlideDirection.Start
//                )
//            },
//            exitTransition = {
//                fadeOut(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
//                ) + slideOutOfContainer(
//                    animationSpec = tween(300, easing = EaseOut),
//                    towards = AnimatedContentTransitionScope.SlideDirection.End
//                )
//            },
        ) { backStackEntry ->
            val name = backStackEntry.savedStateHandle.get<String>("name")
            DetailPagePanel(
                navController = navController,
                name = name!!,
            )
        }
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                if (AppearanceStateStore.isShowMusicVerna() || AppearanceStateStore.isShowMusicEffect()) {
                    if (Instance.mixerPlayerService.getIsPlaying()) {
                        AppearanceStateStore.voiceHigh =
                            Instance.mixerPlayerService.getCurrentVoiceHigh()
                    } else if (Instance.listPlayerService.getIsPlaying()) {
                        AppearanceStateStore.voiceHigh =
                            Instance.listPlayerService.getCurrentVoiceHigh()
                    } else {
                        if (!Instance.mixerPlayerService.isInitialled() &&
                            !Instance.listPlayerService.isInitialled()
                        ) {
                            AppearanceStateStore.voiceHigh = FloatArray(0)
                        }
                    }
                    delay(16)
                } else {
                    delay(200)
                }
            }
        }
    }

    /** 音频可视化 */
    if (!Instance.appearanceService.isWatch()) {
        MusicEffectPanel()
    }

}
