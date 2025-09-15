package com.crimson.app.heartsteel.ui.page.home.tab.b_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.model.TraitSave
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.store.ListPlayerStateStore
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.content.ListColumnContentPanel
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.content.ListPlayerControlPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

val previewData = TraitSave(
    uid = "test___123456",
    name = "心之钢",
    image = "TFT10_Item_PBJEmblem",
    step = "1",
    trackList = listOf(
        "heartsteel_1_bg",
        "heartsteel_1_ry",
        "heartsteel_1_tr",
        "glitterbomb_1_bg",
        "s_1_jin"
    ),
    trackMap = mapOf(
        "ry" to listOf(TraitEnum.TraitHeartsteel),
        "bg" to listOf(TraitEnum.TraitHeartsteel, TraitEnum.TraitHyperpop),
        "tr" to listOf(TraitEnum.TraitHeartsteel),
        "s" to listOf(TraitEnum.TraitClassical),
    )
)

@Composable
@Preview
fun ListPanel(
    callbackHeader: (header: @Composable () -> Unit) -> Unit = {}
) {

    val isPreview = LocalInspectionMode.current

    var isEditing by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var playItemUid by remember { mutableStateOf("") }
    val traitSaveList = remember {
        mutableStateListOf<TraitSave>().apply {
            if (isPreview) {
                add(previewData)
            } else {
                addAll(ListPlayerStateStore.traitSaveList)
            }
        }
    }

    var isPlaying by remember { mutableStateOf(ListPlayerStateStore.isPlaying) }
    var currentPlayUid by remember { mutableStateOf(ListPlayerStateStore.currentPlayUid) }
    var currentPlayTime by remember { mutableStateOf(ListPlayerStateStore.currentPlayTime) }
    var isControlEnable by remember { mutableStateOf(ListPlayerStateStore.isControlEnable) }

    val saveToStorage = {
        ListPlayerStateStore.saveTraitSaveList(traitSaveList)
    }

    val playStateCallback = { result: Boolean ->
        isPlaying = result
    }

    val playUidUpdateCallback = { result: String ->
        currentPlayUid = result
    }

    val playTimeCallback = { result: PlayTime ->
        currentPlayTime = result
    }

    val controlEnableCallback = { result: Boolean ->
        isControlEnable = result
    }

    LaunchedEffect(Unit) {
        // 更新播放列表
        ListPlayerStateStore.loadTraitSaveList(onResult = { result ->
            traitSaveList.clear()
            traitSaveList.addAll(result)
        })
        // 获取播放器状态值
        isPlaying = Instance.listPlayerService.getIsPlaying()
        currentPlayUid = Instance.listPlayerService.getCurrentPlayUid()
        currentPlayTime = Instance.listPlayerService.getCurrentPlayTime()
        isControlEnable = Instance.listPlayerService.getIsControlEnable()
    }

    DisposableEffect(Unit) {
        // 监听播放器事件
        val u1 = Instance.listPlayerService.addPlayStateCallback(playStateCallback)
        val u2 = Instance.listPlayerService.addPlayUidUpdateCallback(playUidUpdateCallback)
        val u3 = Instance.listPlayerService.addPlayTimeCallback(playTimeCallback)
        val u4 = Instance.listPlayerService.addControlEnableCallback(controlEnableCallback)
        onDispose {
            Instance.listPlayerService.removePlayStateCallback(u1)
            Instance.listPlayerService.removePlayUidUpdateCallback(u2)
            Instance.listPlayerService.removePlayTimeCallback(u3)
            Instance.listPlayerService.removeControlEnableCallback(u4)
        }
    }

    callbackHeader {
        HeaderPanel(text = "播放列表", right = {
            IconButton(
                onClick = {
                    isEditing = !isEditing
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if (isEditing) {
                        Icons.Outlined.Check
                    } else {
                        Icons.Outlined.EditNote
                    },
                    contentDescription = if (isEditing) {
                        "完成"
                    } else {
                        "编辑"
                    },
                )
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .then(
                    if (AppearanceStateStore.isRoundWatch()) {
                        Modifier.padding(horizontal = 56.dp)
                    } else {
                        Modifier
                    }
                ),
        ) {
            ListColumnContentPanel(
                traitSaveList = traitSaveList,
                isEditing = isEditing,
                isPlaying = isPlaying,
                currentPlayUid = currentPlayUid,
                isControlEnable = isControlEnable,
                updateListCallback = { list ->
                    traitSaveList.clear()
                    traitSaveList.addAll(list)
                },
                saveCallback = saveToStorage,
                playItemCallback = { uid ->
                    if (Instance.mixerPlayerService.isInitialled()) {
                        playItemUid = uid
                        showConfirmDialog = true
                    } else {
                        Instance.listPlayerService.listPlay(uid)
                    }
                },
                pauseItemCallback = { uid ->
                    Instance.listPlayerService.pause()
                },
            )
        }
        ListPlayerControlPanel(
            traitSaveList = traitSaveList,
            isPlaying = isPlaying,
            currentPlayUid = currentPlayUid,
            currentPlayTime = currentPlayTime,
            isControlEnable = isControlEnable,
            onPre = {
                Instance.listPlayerService.playPre()
            },
            onPlay = {
                if (Instance.mixerPlayerService.isInitialled()) {
                    playItemUid = currentPlayUid
                    showConfirmDialog = true
                } else {
                    Instance.listPlayerService.listPlay(currentPlayUid)
                }
            },
            onPause = {
                Instance.listPlayerService.pause()
            },
            onNext = {
                Instance.listPlayerService.playNext()
            },
            onCurrentTurnTo = { timeMillis ->
                Instance.listPlayerService.turnTo(timeMillis)
            }
        )
    }

    when {
        showConfirmDialog -> {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("确认切换") },
                text = { Text("这将结束正在播放的混音。") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showConfirmDialog = false
                            MixerPlayerStateStore.reset()
                            Instance.mixerPlayerService.lockControl()
                            Instance.listPlayerService.lockControl()
                            Instance.mixerPlayerService.release(onFinish = {
                                Instance.listPlayerService.unlockControl()
                                Instance.listPlayerService.listPlay(playItemUid, onFinish = {
                                    Instance.mixerPlayerService.unlockControl()
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