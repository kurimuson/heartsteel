package com.crimson.app.heartsteel.ui.page.home.tab.b_list.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.common.imageResourceMap
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitSave
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.previewData
import com.crimson.app.heartsteel.util.CommonUtil
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ListPlayerControlPanel(
    traitSaveList: SnapshotStateList<TraitSave> = mutableStateListOf(previewData),
    isPlaying: Boolean = false,
    currentPlayUid: String = previewData.uid,
    currentPlayTime: PlayTime = PlayTime(121500, 205500),
    isControlEnable: Boolean = true,
    onPre: () -> Unit = {},
    onPlay: () -> Unit = {},
    onPause: () -> Unit = {},
    onNext: () -> Unit = {},
    onCurrentTurnTo: (Int) -> Unit = {},
) {

    var currentTimeText by remember {
        mutableStateOf(CommonUtil.formatMillisToTime(currentPlayTime.currentTimeMillis))
    }
    var allTimeText by remember {
        mutableStateOf(CommonUtil.formatMillisToTime(currentPlayTime.allTimeMillis))
    }
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

    val traitSave by remember(traitSaveList, currentPlayUid) {
        derivedStateOf {
            traitSaveList.firstOrNull { it.uid == currentPlayUid }
        }
    }

    LaunchedEffect(currentPlayTime) {
        // 时间显示
        currentTimeText = CommonUtil.formatMillisToTime(currentPlayTime.currentTimeMillis)
        allTimeText = CommonUtil.formatMillisToTime(currentPlayTime.allTimeMillis)
        // 进度条
        if (!isSliderDragging && isControlEnable) {
            if (currentPlayTime.allTimeMillis > 0) {
                sliderPosition =
                    currentPlayTime.currentTimeMillis.toFloat() / currentPlayTime.allTimeMillis.toFloat()
            } else {
                sliderPosition = 0f
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                if (traitSave != null) {
                    val image = imageResourceMap[traitSave!!.image]
                    if (image != null) {
                        val imageModifier = if (AppearanceStateStore.isRoundWatch()) {
                            Modifier
                                .padding(start = 20.dp)
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                        } else {
                            Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                        }
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(image),
                            contentDescription = "TraitIcon",
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            text = if (traitSave == null) {
                                "等待播放"
                            } else {
                                traitSave!!.name
                            },
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${currentTimeText}/${allTimeText}",
                            fontSize = 14.sp,
                            maxLines = 1,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        IconButton(
                            enabled = isControlEnable && currentPlayUid.isNotEmpty(),
                            onClick = onPre
                        ) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                imageVector = Icons.Filled.FastRewind,
                                contentDescription = "上一首",
                            )
                        }
                        if (isPlaying) {
                            IconButton(
                                enabled = isControlEnable && traitSaveList.isNotEmpty(),
                                modifier = Modifier.size(56.dp),
                                onClick = onPause
                            ) {
                                Icon(
                                    modifier = Modifier.size(56.dp),
                                    imageVector = Icons.Filled.PauseCircle,
                                    contentDescription = "暂停",
                                )
                            }
                        } else {
                            IconButton(
                                enabled = isControlEnable && traitSaveList.isNotEmpty(),
                                modifier = Modifier.size(56.dp),
                                onClick = onPlay
                            ) {
                                Icon(
                                    modifier = Modifier.size(56.dp),
                                    imageVector = Icons.Filled.PlayCircle,
                                    contentDescription = "播放",
                                )
                            }
                        }
                        IconButton(
                            enabled = isControlEnable && currentPlayUid.isNotEmpty(),
                            onClick = onNext
                        ) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                imageVector = Icons.Filled.FastForward,
                                contentDescription = "下一首",
                            )
                        }
                    }
                }
            }
            val sliderModifier = if (AppearanceStateStore.isRoundWatch()) {
                Modifier.fillMaxWidth()
                    .padding(horizontal = 48.dp)
            } else {
                Modifier.fillMaxWidth()
            }
            Slider(
                modifier = sliderModifier,
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
                        val timeMillis = (sliderPosition * currentPlayTime.allTimeMillis).toInt()
                        onCurrentTurnTo(timeMillis)
                        isSliderDragging = false
                    }
                }
            )
        }
    }

}