package com.crimson.app.heartsteel.ui.page.home.tab.a_mixer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.QueueMusic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content.MixerPlayerControlPanel
import com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content.MixerPlayerControlPanelRef
import com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content.MixerStepPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

val titles = listOf("一阶段", "二阶段")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MixerPanel(
    callbackHeader: (header: @Composable () -> Unit) -> Unit = {}
) {

    var showHelpAlertDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var targetTabIndex by remember { mutableIntStateOf(0) }
    var selectedPanel by rememberSaveable { mutableIntStateOf(MixerPlayerStateStore.selectedPanel) }

    val trackList = remember { mutableStateListOf<String>() }
    var playerControllerRef by remember { mutableStateOf<MixerPlayerControlPanelRef?>(null) }

    LaunchedEffect(playerControllerRef) {
        if (playerControllerRef != null) {
            playerControllerRef!!.updateStep(selectedPanel + 1)
        }
        if (playerControllerRef != null && trackList.isNotEmpty()) {
            playerControllerRef!!.updateTrackList(trackList)
        }
    }

    // 选择事件回调
    val onTrackListChange: (List<String>) -> Unit = { result ->
        trackList.clear()
        trackList.addAll(result)
        playerControllerRef?.updateTrackList(result)
    }

    callbackHeader {
        HeaderPanel(text = "混音器", right = {
            IconButton(
                onClick = {
                    showHelpAlertDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.AutoMirrored.Outlined.QueueMusic,
                    contentDescription = "提示",
                )
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SecondaryTabRow(selectedTabIndex = selectedPanel) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedPanel == index,
                    onClick = {
                        if (selectedPanel != index) {
                            if (Instance.mixerPlayerService.isInitialled()) {
                                targetTabIndex = index
                                showConfirmDialog = true
                            } else {
                                selectedPanel = index
                                MixerPlayerStateStore.selectedPanel = index
                                playerControllerRef?.updateStep(selectedPanel + 1)
                            }
                        }
                    },
                    text = {
                        Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .then(
                    if (AppearanceStateStore.isRoundWatch()) {
                        Modifier.padding(horizontal = 18.dp)
                    } else {
                        Modifier
                    }
                ),
        ) {
            when (selectedPanel) {
                0 -> MixerStepPanel("1", onTrackListChange)
                1 -> MixerStepPanel("2", onTrackListChange)
            }
        }
        MixerPlayerControlPanel(
            ref = { ref -> playerControllerRef = ref },
            onPlayClick = {
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
                            selectedPanel = targetTabIndex
                            MixerPlayerStateStore.selectedPanel = targetTabIndex
                            Instance.mixerPlayerService.lockControl()
                            Instance.listPlayerService.lockControl()
                            Instance.mixerPlayerService.release(onFinish = {
                                Instance.mixerPlayerService.unlockControl()
                                Instance.listPlayerService.unlockControl()
                                playerControllerRef?.resetSlider()
                                playerControllerRef?.updateStep(selectedPanel + 1)
                                playerControllerRef?.updateTrackList(
                                    MixerPlayerStateStore.getTrackList(
                                        (selectedPanel + 1).toString()
                                    )
                                )
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

    when {
        showHelpAlertDialog -> {
            AlertDialog(
                onDismissRequest = { showHelpAlertDialog = false },
                title = { Text("提示") },
                text = { Text("为了保证混音器性能与良好的听感，建议最大音轨数量不要超过5个。") },
                confirmButton = {
                    TextButton(
                        onClick = { showHelpAlertDialog = false }
                    ) {
                        Text("确定")
                    }
                }
            )
        }
    }

}
