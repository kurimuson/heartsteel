package com.crimson.app.heartsteel.ui.page.home.tab.b_list.content.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DragHandle
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.UnfoldLess
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.common.imageResourceMap
import com.crimson.app.heartsteel.model.TraitSave
import com.crimson.app.heartsteel.ui.component.TraitChipComponent
import com.crimson.app.heartsteel.ui.dialog.TraitListSaverDialog
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.previewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TraitItemPanel(
    itemList: List<TraitSave> = listOf(previewData),
    index: Int = 0,
    isEditing: Boolean = false,
    isPlaying: Boolean = false,
    currentPlayUid: String = previewData.uid,
    isControlEnable: Boolean = true,
    showMusicVerna: Boolean = false,
    musicVernaColor: Color = Color(0xFF8888DD),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    draggableButton: @Composable () -> Unit = {
        IconButton(
            onClick = {},
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.DragHandle,
                contentDescription = "拖动"
            )
        }
    },
    onItemEdit: (item: TraitSave) -> Unit = { },
    onPlay: () -> Unit = { },
    onPause: () -> Unit = { },
    onRemove: () -> Unit = { },
) {

    val item = itemList[index]
    val isPreview = LocalInspectionMode.current
    var showContent by rememberSaveable { mutableStateOf(isPreview) }
    var showSaverDialog by remember { mutableStateOf(false) }

    val saveToStorage: (name: String, image: String) -> Unit = { name, image ->
        item.name = name
        item.image = image
        onItemEdit(item)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(
                if (showMusicVerna && currentPlayUid == item.uid) {
                    Modifier
                        .border(width = 2.dp, color = musicVernaColor, shape = CardDefaults.shape)
                        .shadow(elevation = 8.dp, clip = false, shape = CardDefaults.shape)
                } else Modifier
            ),
        interactionSource = interactionSource,
        onClick = {
        },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(42.dp)
                        .then(
                            if (showMusicVerna) {
                                Modifier.padding(start = 2.dp, end = 4.dp)
                            } else {
                                Modifier.padding(start = 0.dp, end = 6.dp)
                            }
                        ).background(
                            if (currentPlayUid == item.uid) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Unspecified
                            }
                        )
                ) {}
                val image = imageResourceMap[item.image]
                if (image != null) {
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(image),
                        contentDescription = "TraitIcon",
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    text = item.name,
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    color = if (currentPlayUid == item.uid) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    }
                )
                if (isEditing) {
                    IconButton(
                        onClick = {
                            showSaverDialog = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Outlined.EditNote,
                            contentDescription = "编辑"
                        )
                    }
                    IconButton(
                        onClick = onRemove
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "删除"
                        )
                    }
                    draggableButton()
                } else {
                    if (item.uid == currentPlayUid && isPlaying) {
                        IconButton(
                            enabled = isControlEnable,
                            onClick = onPause,
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Outlined.PauseCircle,
                                contentDescription = "暂停",
                                tint = if (currentPlayUid == item.uid) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    LocalContentColor.current
                                },
                            )
                        }
                    } else {
                        IconButton(
                            enabled = isControlEnable,
                            onClick = onPlay
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Outlined.PlayCircle,
                                contentDescription = "播放",
                                tint = if (currentPlayUid == item.uid) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    LocalContentColor.current
                                },
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            showContent = !showContent
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = if (showContent) Icons.Outlined.UnfoldLess else Icons.Outlined.ExpandMore,
                            contentDescription = if (showContent) "折叠" else "展开"
                        )
                    }
                }
            }
            AnimatedVisibility(showContent) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    val trackTypes = remember {
                        listOf(
                            "主音：" to "ry",
                            "鼓组：" to "tr",
                            "伴奏：" to "bg",
                        )
                    }
                    val step = remember {
                        if (item.step == "1") {
                            "一阶段/"
                        } else {
                            "二阶段/"
                        }
                    }
                    for ((title, key) in trackTypes) {
                        val traitList = item.trackMap[key]
                        if (traitList != null && traitList.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = step + title,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Start,
                                )
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    for (trait in traitList) {
                                        TraitChipComponent(trait)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    when {
        showSaverDialog -> {
            TraitListSaverDialog(
                name = item.name,
                image = item.image,
                onDismissRequest = {
                    showSaverDialog = false
                },
                onConfirmation = { name, image ->
                    showSaverDialog = false
                    saveToStorage(name, image)
                }
            )
        }
    }

}