package com.crimson.app.heartsteel.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.MusicEffectColorEnum
import com.crimson.app.heartsteel.store.AppearanceStateStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

data class RectState(
    var width: Float = 0.0f,
    var height: Float = 0.0f
)

data class DotState(
    var cap: Float = 0.0f,
    var y: Float = 0.0f,
    var bottom: Float = 25.0f,
    var width: Float = 0.0f,
    var height: Float = 6.0f,
)

@Composable
fun MusicEffectPanel() {

    var showMusicEffect by remember { mutableStateOf(AppearanceStateStore.isShowMusicEffect()) }
    var musicEffectColor by remember { mutableStateOf(AppearanceStateStore.value.musicEffectColor) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // 单个柱子宽度
    val rectWidth = with(density) { 3.dp.toPx() }
    // 柱子之间的间距
    val spacing = with(density) { 4.dp.toPx() }

    // 屏幕尺寸
    var screenWidth by remember { mutableFloatStateOf(0f) }
    var screenHeight by remember { mutableFloatStateOf(0f) }

    // 动态计算柱状数量（单边），确保占满屏幕
    val num by remember(screenWidth) {
        derivedStateOf {
            if (screenWidth == 0f) 1 // 初始值
            else {
                // 计算单边柱子数量 =（半屏宽度 * 1.5）/ 间距
                val halfScreenWidth = screenWidth / 2f
                val maxNum = (halfScreenWidth * 1.5f / spacing).toInt()
                maxNum.coerceAtLeast(1) // 至少保留1个柱子
            }
        }
    }

    val rectList = remember { mutableListOf<RectState>() }
    val dotList = remember { mutableListOf<DotState>() }
    val rectListBundle = remember { mutableStateListOf<RectState>() }
    val dotListBundle = remember { mutableStateListOf<DotState>() }

    val themePrimaryColor = MaterialTheme.colorScheme.primary

    val gradientBrush = remember(musicEffectColor, themePrimaryColor, screenWidth, screenHeight) {
        mutableStateOf(
            when (musicEffectColor) {
                MusicEffectColorEnum.Default -> {
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00FF00),  // 绿色
                            Color(0xFFFF0000),  // 红色
                            Color(0xFFFF0000),  // 红色
                            Color(0xFFFF00FF),  // 紫色
                        ),
                        start = Offset(
                            screenWidth * 0.5f,
                            screenHeight - with(density) { 72.dp.toPx() }),
                        end = Offset(screenWidth * 0.5f, screenHeight),
                        tileMode = androidx.compose.ui.graphics.TileMode.Clamp
                    )
                }

                MusicEffectColorEnum.Cyan -> {
                    Brush.linearGradient(
                        colors = listOf(Color.Cyan, Color.Cyan),
                        start = Offset(screenWidth, screenHeight),
                        end = Offset(screenWidth, screenHeight),
                        tileMode = androidx.compose.ui.graphics.TileMode.Clamp
                    )
                }

                MusicEffectColorEnum.FollowTheme -> {
                    Brush.linearGradient(
                        colors = listOf(themePrimaryColor, themePrimaryColor),
                        start = Offset(screenWidth, screenHeight),
                        end = Offset(screenWidth, screenHeight),
                        tileMode = androidx.compose.ui.graphics.TileMode.Clamp
                    )
                }
            }
        )
    }

    var realtimeFps = remember { 60f }
    val dotDropSpeed = remember { with(density) { 0.5.dp.toPx() } }

    val render = { voiceHigh: FloatArray ->
        if (rectList.size != num) {
            rectList.clear()
            for (i in 0 until num) {
                rectList.add(RectState())
            }
        }
        if (dotList.size != num) {
            dotList.clear()
            for (i in 0 until num) {
                dotList.add(
                    DotState(
                        cap = with(density) { 0.dp.toPx() },
                        y = with(density) { 0.dp.toPx() },
                        bottom = with(density) { 8.dp.toPx() },
                        width = rectWidth,
                        height = with(density) { 2.dp.toPx() },
                    )
                )
            }
        }
        val step = (voiceHigh.size / num).coerceAtLeast(1)
        for (i in 0 until num) {
            val value = voiceHigh.getOrNull(step * i) ?: 0f
            // rect
            val rectHeight = with(density) {
                (value.toDouble().pow(1.25) / 5.0).toFloat().dp.toPx()
            }
            val offsetY = dotList[i].bottom + dotList[i].height
            rectList[i].width = rectWidth
            rectList[i].height = rectHeight - offsetY
            // dot
            val realDotDropSpeed = dotDropSpeed * ((1000 / 16).toFloat() / realtimeFps)
            dotList[i].y = screenHeight - dotList[i].cap + offsetY
            dotList[i].cap = dotList[i].cap - realDotDropSpeed
            if (dotList[i].cap <= rectHeight + dotList[i].bottom) {
                dotList[i].cap = rectHeight + dotList[i].bottom
            }
        }
        rectListBundle.clear()
        rectListBundle.addAll(rectList)
        dotListBundle.clear()
        dotListBundle.addAll(dotList)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                if (Instance.appearanceService.isForeground() && showMusicEffect) {
                    val startTime = System.currentTimeMillis()
                    render(AppearanceStateStore.voiceHigh)
                    delay(16)
                    realtimeFps = 1000f / (System.currentTimeMillis() - startTime).toFloat()
                } else {
                    delay(200)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        AppearanceStateStore.addShowMusicEffectChangeCallback { value -> showMusicEffect = value }
        AppearanceStateStore.addMusicEffectColorChangeCallback { value -> musicEffectColor = value }
        onDispose {
            AppearanceStateStore.removeShowMusicEffectChangeCallback()
            AppearanceStateStore.removeMusicEffectColorChangeCallback()
        }
    }

    // 绘制Canvas
    when {
        showMusicEffect ->
            Canvas(modifier = Modifier.fillMaxSize().alpha(0.8f)) {
                screenWidth = size.width
                screenHeight = size.height
                for (item in rectListBundle.withIndex()) {
                    val rightX = size.width * 0.5f + item.index * spacing
                    drawRect(
                        brush = gradientBrush.value,
                        topLeft = Offset(
                            x = rightX,
                            y = size.height - item.value.height
                        ),
                        size = Size(
                            width = item.value.width,
                            height = item.value.height
                        ),
                        style = Fill
                    )
                    drawRect(
                        brush = gradientBrush.value,
                        topLeft = Offset(
                            x = rightX,
                            y = dotList[item.index].y
                        ),
                        size = Size(
                            width = dotList[item.index].width,
                            height = dotList[item.index].height
                        ),
                        style = Fill
                    )
                    val leftX = size.width * 0.5f - (item.index + 1) * spacing
                    drawRect(
                        brush = gradientBrush.value,
                        topLeft = Offset(
                            x = leftX,
                            y = size.height - item.value.height
                        ),
                        size = Size(
                            width = item.value.width,
                            height = item.value.height
                        ),
                        style = Fill
                    )
                    drawRect(
                        brush = gradientBrush.value,
                        topLeft = Offset(
                            x = leftX,
                            y = dotList[item.index].y
                        ),
                        size = Size(
                            width = dotList[item.index].width,
                            height = dotList[item.index].height
                        ),
                        style = Fill
                    )
                }
            }
    }

}
