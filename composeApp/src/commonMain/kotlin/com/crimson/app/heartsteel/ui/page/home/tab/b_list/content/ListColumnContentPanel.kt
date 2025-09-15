package com.crimson.app.heartsteel.ui.page.home.tab.b_list.content

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DragHandle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.TraitSave
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.content.item.TraitItemPanel
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.previewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

data class ColorState(
    val color: Color,
    val hue: Float
)

// 参数配置（移除冗余，合理利用所有属性）
object ColorConfig {
    // 前四分之一的长度（128/4=32）
    const val QUARTER_LENGTH = 16

    // 红色阈值：前四分之一总和超过此值倾向红色
    const val RED_THRESHOLD = 200f * QUARTER_LENGTH  // 调小→更容易变红

    // 蓝色阈值：前四分之一总和低于此值倾向蓝色
    const val BLUE_THRESHOLD = 50f * QUARTER_LENGTH  // 调大→更容易变蓝

    // 色相过渡速度（每帧最大变化）
    const val MAX_HUE_STEP = 50f

    // 红色区域最大色相（0°-RED_HUE_RANGE为红色系）
    const val RED_HUE_RANGE = 60f     // 例如60°：红→橙

    // 蓝色区域最小色相（BLUE_HUE_RANGE-240°为蓝色系）
    const val BLUE_HUE_RANGE = 180f   // 例如180°：青→蓝
}

fun smoothSpectrumToColor(
    voiceHigh: FloatArray,
    lastColor: Color,
    lastHue: Float,
    fps: Float,
): ColorState {
    // 1. 抽稀为128长度数组
    val downsampled = downsampleTo128(voiceHigh)
    if (downsampled.size < ColorConfig.QUARTER_LENGTH) {
        val target = Color(0xFF3355AA)
        val newColor = blendColor(lastColor, target, 0.2f)
        return ColorState(newColor, 240f)
    }

    // 2. 计算前四分之一的总和（核心判断依据）
    val quarterSum = downsampled.take(ColorConfig.QUARTER_LENGTH).sum().coerceAtLeast(0f)

    // 3. 归一化前四分之一总和（0~1）
    val normalizedSum = when {
        quarterSum >= ColorConfig.RED_THRESHOLD -> 1f
        quarterSum <= ColorConfig.BLUE_THRESHOLD -> 0f
        else -> (quarterSum - ColorConfig.BLUE_THRESHOLD) /
                (ColorConfig.RED_THRESHOLD - ColorConfig.BLUE_THRESHOLD)
    }.coerceIn(0f, 1f)

    // 4. 利用RED_HUE_RANGE和BLUE_HUE_RANGE计算色相
    // 逻辑：总和越大→色相从BLUE_HUE_RANGE向0°靠近（红色系）
    //       总和越小→色相从RED_HUE_RANGE向240°靠近（蓝色系）
    val hueRange = 240f - ColorConfig.RED_HUE_RANGE // 总过渡范围
    val targetHue = if (normalizedSum >= 0.5f) {
        // 红色系过渡（0° ~ RED_HUE_RANGE）
        ColorConfig.RED_HUE_RANGE - (normalizedSum - 0.5f) * 2 * ColorConfig.RED_HUE_RANGE
    } else {
        // 蓝色系过渡（BLUE_HUE_RANGE ~ 240°）
        ColorConfig.BLUE_HUE_RANGE + (0.5f - normalizedSum) * 2 * (240f - ColorConfig.BLUE_HUE_RANGE)
    }.coerceIn(0f, 240f)

    // 5. 平滑过渡
    val step = ColorConfig.MAX_HUE_STEP * ((1000 / 16).toFloat() / fps)
    val hueStep = targetHue - lastHue
    val clampedStep = hueStep.coerceIn(-step, step)
    val currentHue = lastHue + clampedStep

    // 6. 饱和度和亮度
    val saturation = 0.8f + (normalizedSum * 0.2f).coerceIn(0f, 0.2f)
    val brightness = 0.5f + (normalizedSum * 0.4f).coerceIn(0f, 0.4f)

    // 7. 融合比例
    val blendRatio = if (normalizedSum > 0.8f || normalizedSum < 0.2f) 0.8f else 0.5f

    // 8. 生成颜色
    val targetColor = Color.hsv(
        hue = currentHue.coerceIn(0f, 360f),
        saturation = saturation,
        value = brightness
    )
    val finalColor = blendColor(lastColor, targetColor, blendRatio)

    return ColorState(finalColor, currentHue)
}

// 抽稀函数
private fun downsampleTo128(original: FloatArray): FloatArray {
    if (original.size <= 128) {
        return original.copyOf(128)
    }
    val downsampled = FloatArray(128)
    val originalSize = original.size
    for (i in 0 until 128) {
        val originalIndex = (i * (originalSize - 1).toFloat() / 127f).toInt()
        downsampled[i] = original[originalIndex]
    }
    return downsampled
}

private fun blendColor(color1: Color, color2: Color, ratio: Float): Color {
    val r = color1.red * (1 - ratio) + color2.red * ratio
    val g = color1.green * (1 - ratio) + color2.green * ratio
    val b = color1.blue * (1 - ratio) + color2.blue * ratio
    return Color(r, g, b, 1f)
}


@Composable
@Preview
fun ListColumnContentPanel(
    traitSaveList: SnapshotStateList<TraitSave> = mutableStateListOf(previewData),
    isEditing: Boolean = false,
    isPlaying: Boolean = false,
    currentPlayUid: String = "",
    isControlEnable: Boolean = true,
    updateListCallback: (list: List<TraitSave>) -> Unit = {},
    saveCallback: () -> Unit = {},
    playItemCallback: (uid: String) -> Unit = {},
    pauseItemCallback: (uid: String) -> Unit = {},
) {

    val scope = rememberCoroutineScope()
    val hapticFeedback = LocalHapticFeedback.current
    var showMusicVerna by remember { mutableStateOf(AppearanceStateStore.isShowMusicVerna()) }

    var showRemoveDialog by remember { mutableStateOf(false) }
    var removeItemUid by remember { mutableStateOf("") }
    var itemList by remember { mutableStateOf(traitSaveList.toList()) }

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        itemList = itemList.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        updateListCallback(itemList)
        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    val removeItem: (uid: String) -> Unit = { uid ->
        val targetIndex = itemList.indexOfFirst { it.uid == uid }
        if (targetIndex != -1) {
            itemList = itemList.toMutableList().apply {
                removeAt(targetIndex)
            }
        }
        updateListCallback(itemList)
        saveCallback()
    }

    data class ColorState(
        val color: Color,
        val hue: Float
    )

    var realtimeFps = remember { 60f }

    var colorState by remember {
        mutableStateOf(
            ColorState(
                color = Color(0xFF8888DD), // 初始蓝色
                hue = 240f // 初始蓝色色相
            )
        )
    }

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                if (Instance.appearanceService.isForeground() && AppearanceStateStore.isShowMusicVerna()) {
                    val startTime = System.currentTimeMillis()
                    // 调用函数时传入当前状态的颜色和色相
                    val result = smoothSpectrumToColor(
                        voiceHigh = AppearanceStateStore.voiceHigh,
                        lastColor = colorState.color,
                        lastHue = colorState.hue,
                        fps = realtimeFps,
                    )
                    // 更新状态（同时保存新颜色和新色相）
                    colorState = colorState.copy(color = result.color, hue = result.hue)
                    delay(16) // 保持60帧/秒的更新频率
                    realtimeFps = 1000f / (System.currentTimeMillis() - startTime).toFloat()
                } else {
                    delay(200)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        AppearanceStateStore.addShowMusicVernaChangeCallback { value -> showMusicVerna = value }
        onDispose {
            AppearanceStateStore.removeShowMusicVernaChangeCallback()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = itemList.size,
            key = { index -> itemList[index] }
        ) { index ->

            val item = itemList[index]

            ReorderableItem(reorderableLazyListState, key = item) {

                val interactionSource = remember { MutableInteractionSource() }

                TraitItemPanel(
                    itemList = itemList,
                    index = index,
                    isEditing = isEditing,
                    isPlaying = isPlaying,
                    currentPlayUid = currentPlayUid,
                    isControlEnable = isControlEnable,
                    showMusicVerna = showMusicVerna,
                    musicVernaColor = colorState.color,
                    interactionSource = interactionSource,
                    draggableButton = {
                        IconButton(
                            modifier = Modifier
                                .draggableHandle(
                                    onDragStarted = {
                                        hapticFeedback.performHapticFeedback(
                                            HapticFeedbackType.GestureThresholdActivate
                                        )
                                    },
                                    onDragStopped = {
                                        hapticFeedback.performHapticFeedback(
                                            HapticFeedbackType.GestureEnd
                                        )
                                        saveCallback()
                                    },
                                    interactionSource = interactionSource,
                                ),
                            onClick = {},
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Outlined.DragHandle,
                                contentDescription = "拖动"
                            )
                        }
                    },
                    onItemEdit = { item ->
                        itemList = mutableListOf<TraitSave>().apply {
                            addAll(itemList)
                        }
                        itemList.toMutableList()[index] = item
                        updateListCallback(itemList)
                        saveCallback()
                    },
                    onPause = {
                        pauseItemCallback(item.uid)
                    },
                    onPlay = {
                        playItemCallback(item.uid)
                    },
                    onRemove = {
                        removeItemUid = item.uid
                        showRemoveDialog = true
                    }
                )
            }
        }
    }

    when {
        showRemoveDialog -> {
            AlertDialog(
                onDismissRequest = { showRemoveDialog = false },
                title = { Text("是否删除") },
                text = { Text("这将删除该项。") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (removeItemUid.isNotEmpty()) {
                                removeItem(removeItemUid)
                            }
                            showRemoveDialog = false
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showRemoveDialog = false }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
    }

}
