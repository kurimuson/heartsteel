package com.crimson.app.heartsteel.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crimson.app.heartsteel.common.traitInfoMap
import com.crimson.app.heartsteel.common.traitKeyListMap
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.ui.component.TraitIconComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TraitListSelectorDialog(
    type: String = "ry",
    default: List<TraitEnum> = listOf(TraitEnum.TraitHeartsteel),
    onDismissRequest: () -> Unit = {},
    onConfirmation: (result: List<TraitEnum>) -> Unit = {},
) {

    val scrollState = rememberScrollState()
    val traitKeyList = traitKeyListMap[type]!!
    val traitSelectMap = remember {
        mutableStateMapOf<TraitEnum, Boolean>().apply {
            traitKeyList.forEach { key -> put(key, default.indexOf(key) != -1) }
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    max = if (Instance.appearanceService.isWatch()) {
                        400.dp
                    } else {
                        575.dp
                    }
                )
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    for (traitKey in traitKeyList) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    // 点击整行时切换选中状态
                                    val currentState = traitSelectMap[traitKey] ?: false
                                    traitSelectMap[traitKey] = !currentState
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Checkbox(
                                checked = traitSelectMap[traitKey] ?: false,
                                onCheckedChange = { isChecked ->
                                    traitSelectMap[traitKey] = isChecked
                                }
                            )
                            val icon = remember { traitInfoMap[traitKey]!!.icon }
                            if (icon != null) {
                                Box(
                                    modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                                ) {
                                    TraitIconComponent(icon)
                                }
                            }
                            Text(traitInfoMap[traitKey]!!.name)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("取消")
                }
                TextButton(
                    onClick = {
                        val result = mutableListOf<TraitEnum>()
                        traitSelectMap.forEach { (key, value) ->
                            if (value) {
                                result.add(key)
                            }
                        }
                        onConfirmation(result)
                    },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("确定")
                }
            }
        }
    }

}