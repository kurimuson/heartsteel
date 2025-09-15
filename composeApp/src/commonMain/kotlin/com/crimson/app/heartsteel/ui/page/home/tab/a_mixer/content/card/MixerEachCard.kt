package com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.UnfoldLess
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import com.crimson.app.heartsteel.ui.component.TraitChipComponent
import com.crimson.app.heartsteel.ui.dialog.TraitListSelectorDialog
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MixerEachCard(
    step: String = "1",
    type: String = "ry",
    onTraitsSelected: (result: List<TraitEnum>) -> Unit = {},
) {

    val title = when (type) {
        "ry" -> "主音"
        "bg" -> "伴奏"
        "tr" -> "鼓组"
        else -> ""
    }
    var showContent by remember { mutableStateOf(true) }
    var openDialog by remember { mutableStateOf(false) }

    val traitSelectList = remember {
        mutableStateListOf<TraitEnum>().apply {
            addAll(MixerPlayerStateStore.selectedTraitListMap[step]!![type]!!)
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            IconButton(
                onClick = { openDialog = true }
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "选项"
                )
            }
            IconButton(
                onClick = { showContent = !showContent }
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = if (showContent) Icons.Outlined.UnfoldLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (showContent) "折叠" else "展开"
                )
            }
        }
        AnimatedVisibility(showContent) {
            if (traitSelectList.isEmpty()) {
                // 当列表为空时显示提示文本
                Text(
                    text = "请选择...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.outline,
                )
            } else {
                // 列表不为空时显示FlowRow
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    for (trait in traitSelectList) {
                        TraitChipComponent(trait, onClose = {
                            traitSelectList.remove(trait)
                            MixerPlayerStateStore.selectedTraitListMap[step]!![type]!!.clear()
                            MixerPlayerStateStore.selectedTraitListMap[step]!![type]!!.addAll(
                                traitSelectList
                            )
                            onTraitsSelected(traitSelectList)
                        })
                    }
                }
            }
        }
    }

    when {
        openDialog -> {
            TraitListSelectorDialog(
                type = type,
                default = traitSelectList,
                onDismissRequest = { openDialog = false },
                onConfirmation = { result ->
                    openDialog = false
                    traitSelectList.clear()
                    traitSelectList.addAll(result)
                    MixerPlayerStateStore.selectedTraitListMap[step]!![type]!!.clear()
                    MixerPlayerStateStore.selectedTraitListMap[step]!![type]!!.addAll(
                        traitSelectList
                    )
                    onTraitsSelected(traitSelectList)
                },
            )
        }
    }

}