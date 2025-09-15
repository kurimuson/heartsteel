package com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.store.ListPlayerStateStore
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import org.jetbrains.compose.ui.tooling.preview.Preview

// 该播放器组件保留ref写法，作为参考
interface MixerPlayerControlPanelRef {
    fun updateStep(step: Int)
    fun updateTrackList(newTrackList: List<String>)
    fun resetSlider()
}

@Composable
@Preview
fun MixerPlayerControlPanel(
    ref: (MixerPlayerControlPanelRef) -> Unit = {},
    onPlayClick: () -> Unit = {}
) {

    var showConfirmDialog by remember { mutableStateOf(false) }

    var isPlaying by remember { mutableStateOf(MixerPlayerStateStore.isPlaying) }
    var currentPlayTime by remember { mutableStateOf(MixerPlayerStateStore.currentPlayTime) }
    var isControlEnable by remember { mutableStateOf(MixerPlayerStateStore.isControlEnable) }

    var sliderPosition by remember {
        mutableFloatStateOf(
            if (currentPlayTime.allTimeMillis > 0) {
                currentPlayTime.currentTimeMillis.toFloat() / currentPlayTime.allTimeMillis.toFloat()
            } else {
                0f
            }
        )
    }

    var isSliderDragging by remember { mutableStateOf(false) }

    var trackList by remember { mutableStateOf(emptyList<String>()) }

    val useImperativeHandle = remember {
        object : MixerPlayerControlPanelRef {
            override fun updateStep(step: Int) {
                Instance.mixerPlayerService.updateStep(step)
            }

            override fun updateTrackList(newTrackList: List<String>) {
                trackList = newTrackList
                Instance.mixerPlayerService.updateTrackList(newTrackList)
            }

            override fun resetSlider() {
                sliderPosition = 0f
            }
        }
    }

    val playStateCallback = { result: Boolean ->
        isPlaying = result
    }

    val playTimeCallback = { result: PlayTime ->
        currentPlayTime = result
        if (!isSliderDragging && isControlEnable) {
            if (result.allTimeMillis > 0) {
                sliderPosition = result.currentTimeMillis.toFloat() / result.allTimeMillis.toFloat()
            } else {
                sliderPosition = 0f
            }
        }
    }

    val controlEnableCallback = { result: Boolean ->
        isControlEnable = result
    }

    LaunchedEffect(useImperativeHandle) {
        ref(useImperativeHandle)
        // 获取播放器状态值
        isPlaying = Instance.mixerPlayerService.getIsPlaying()
        currentPlayTime = Instance.mixerPlayerService.getCurrentPlayTime()
        isControlEnable = Instance.mixerPlayerService.getIsControlEnable()
    }

    DisposableEffect(Unit) {
        val u1 = Instance.mixerPlayerService.addPlayStateCallback(playStateCallback)
        val u2 = Instance.mixerPlayerService.addPlayTimeCallback(playTimeCallback)
        val u3 = Instance.mixerPlayerService.addControlEnableCallback(controlEnableCallback)
        onDispose {
            Instance.mixerPlayerService.removePlayStateCallback(u1)
            Instance.mixerPlayerService.removePlayTimeCallback(u2)
            Instance.mixerPlayerService.removeControlEnableCallback(u3)
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Row(
            modifier = if (AppearanceStateStore.isRoundWatch()) {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 8.dp)
            } else {
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(
                enabled = isControlEnable,
                onClick = {
                    if (!isPlaying) {
                        if (Instance.listPlayerService.isInitialled()) {
                            showConfirmDialog = true
                        } else {
                            // 播放
                            if (trackList.isNotEmpty()) {
                                Instance.mixerPlayerService.play()
                            }
                        }
                    } else {
                        // 暂停
                        Instance.mixerPlayerService.pause()
                    }
                    // 回调
                    onPlayClick()
                }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if (isPlaying) {
                        Icons.Outlined.PauseCircle
                    } else {
                        Icons.Outlined.PlayCircle
                    },
                    contentDescription = if (isPlaying) {
                        "暂停"
                    } else {
                        "播放"
                    },
                )
            }
            Slider(
                enabled = isControlEnable,
                value = sliderPosition,
                onValueChange = { value ->
                    if (currentPlayTime.allTimeMillis > 0) {
                        sliderPosition = value
                        isSliderDragging = true
                    }
                },
                onValueChangeFinished = {
                    if (currentPlayTime.allTimeMillis > 0) {
                        val newTimeMillis = (sliderPosition * currentPlayTime.allTimeMillis).toInt()
                        Instance.mixerPlayerService.turnTo(newTimeMillis)
                        isSliderDragging = false
                    }
                }
            )
        }
    }

    when {
        showConfirmDialog -> {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("确认切换") },
                text = { Text("这将结束列表播放。") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showConfirmDialog = false
                            ListPlayerStateStore.reset()
                            Instance.mixerPlayerService.lockControl()
                            Instance.listPlayerService.lockControl()
                            Instance.listPlayerService.release(onFinish = {
                                Instance.mixerPlayerService.unlockControl()
                                Instance.mixerPlayerService.play(onFinish = {
                                    Instance.listPlayerService.unlockControl()
                                })
                            })
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showConfirmDialog = false }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
    }

}
