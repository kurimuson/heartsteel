package com.crimson.app.heartsteel.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crimson.app.heartsteel.common.themeMap
import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.theme.normal.primaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ThemeSelectorDialog(
    default: ThemeEnum = ThemeEnum.Normal,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (result: ThemeEnum) -> Unit = {},
) {

    val scrollState = rememberScrollState()
    var showAlertDialog by remember { mutableStateOf(false) }

    var selectedValue by remember { mutableStateOf(default) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp, 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(vertical = 4.dp)
                            .clickable {
                                // 点击整行时切换选中状态
                                val isChecked = selectedValue != ThemeEnum.Normal
                                if (isChecked) {
                                    selectedValue = ThemeEnum.Normal
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Checkbox(
                            checked = selectedValue == ThemeEnum.Normal,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedValue = ThemeEnum.Normal
                                }
                            }
                        )
                        Text(text = "默认")
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp, end = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(primaryLight),
                        ) {}
                    }
                    for (theme in themeMap) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(vertical = 4.dp)
                                .clickable {
                                    // 点击整行时切换选中状态
                                    val isChecked = selectedValue != theme.value.key
                                    if (isChecked) {
                                        selectedValue = theme.value.key
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Checkbox(
                                checked = selectedValue == theme.value.key,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedValue = theme.value.key
                                    }
                                }
                            )
                            Text(text = theme.value.name)
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 12.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                painter = painterResource(theme.value.image),
                                contentDescription = theme.value.name,
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center,
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        onConfirmation(selectedValue)
                    },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("确定")
                }
            }
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            text = { Text("请选择主题。") },
            confirmButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text("确定")
                }
            }
        )
    }

}
