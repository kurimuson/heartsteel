package com.crimson.app.heartsteel.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crimson.app.heartsteel.common.imageNameList
import com.crimson.app.heartsteel.common.imageResourceMap
import com.crimson.app.heartsteel.instance.Instance
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TraitListSaverDialog(
    name: String = "",
    image: String = "",
    onDismissRequest: () -> Unit = {},
    onConfirmation: (name: String, image: String) -> Unit = { name, image -> },
) {

    val scrollState = rememberScrollState()
    var showTextAlertDialog by remember { mutableStateOf(false) }
    var showImageAlertDialog by remember { mutableStateOf(false) }

    var inputText by remember { mutableStateOf(name) }
    var selectedImageName by remember { mutableStateOf(image) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    max = if (Instance.appearanceService.isWatch()) {
                        425.dp
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextField(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        value = inputText,
                        onValueChange = { inputText = it },
                        label = { Text("请输入保存的名字") },
                    )
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .verticalScroll(scrollState),
                        horizontalArrangement = Arrangement.SpaceAround,
                        maxItemsInEachRow = 3
                    ) {
                        for (imageName in imageNameList) {
                            val resource = imageResourceMap[imageName]
                            if (resource != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.325f)
                                        .aspectRatio(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(1.0f * 0.9f)
                                            .aspectRatio(1f)
                                            .align(Alignment.Center)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { selectedImageName = imageName }
                                            .then(
                                                if (imageName == selectedImageName) {
                                                    Modifier
                                                        .border(
                                                            2.dp,
                                                            MaterialTheme.colorScheme.primary,
                                                            RoundedCornerShape(8.dp)
                                                        )
                                                        .shadow(
                                                            8.dp,
                                                            RoundedCornerShape(8.dp),
                                                            clip = false
                                                        )
                                                } else Modifier
                                            )
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            painter = painterResource(resource),
                                            contentDescription = "图片选择项: $imageName",
                                        )
                                        if (imageName == selectedImageName) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Black.copy(alpha = 0.4f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "选中状态",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
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
                        if (inputText.trim().isEmpty()) {
                            showTextAlertDialog = true
                            return@TextButton
                        }
                        if (selectedImageName.isEmpty()) {
                            showImageAlertDialog = true
                            return@TextButton
                        }
                        onConfirmation(inputText.trim(), selectedImageName)
                    },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("确定")
                }
            }
        }
    }

    if (showTextAlertDialog) {
        AlertDialog(
            onDismissRequest = { showTextAlertDialog = false },
            text = { Text("请输入保存的名字。") },
            confirmButton = {
                TextButton(onClick = { showTextAlertDialog = false }) {
                    Text("确定")
                }
            }
        )
    }

    if (showImageAlertDialog) {
        AlertDialog(
            onDismissRequest = { showImageAlertDialog = false },
            text = { Text("请选择图标。") },
            confirmButton = {
                TextButton(onClick = { showImageAlertDialog = false }) {
                    Text("确定")
                }
            }
        )
    }

}
