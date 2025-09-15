package com.crimson.app.heartsteel.ui.dialog

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.crimson.app.heartsteel.common.themeMap
import com.crimson.app.heartsteel.model.MusicEffectColorEnum
import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.theme.normal.primaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MusicEffectColorSelectorDialog(
    default: MusicEffectColorEnum = MusicEffectColorEnum.Default,
    theme: ThemeEnum = ThemeEnum.Normal,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (result: MusicEffectColorEnum) -> Unit = {},
) {

    val density = LocalDensity.current

    val scrollState = rememberScrollState()
    var showAlertDialog by remember { mutableStateOf(false) }

    var selectedValue by remember { mutableStateOf(default) }
    var themeValue by remember { mutableStateOf(theme) }

    var defaultTextWidth by remember { mutableIntStateOf(0) }
    var cyanTextWidth by remember { mutableIntStateOf(0) }
    var themeTextWidth by remember { mutableIntStateOf(0) }

    val maxTextWidth = maxOf(defaultTextWidth, cyanTextWidth, themeTextWidth)

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
                    // 默认
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(vertical = 4.dp)
                            .clickable {
                                // 点击整行时切换选中状态
                                val isChecked = selectedValue != MusicEffectColorEnum.Default
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.Default
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Checkbox(
                            checked = selectedValue == MusicEffectColorEnum.Default,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.Default
                                }
                            }
                        )
                        Text(
                            modifier = Modifier.widthIn(min = with(density) { maxTextWidth.toDp() }),
                            text = "默认",
                            onTextLayout = { layoutResult: TextLayoutResult ->
                                defaultTextWidth = layoutResult.size.width
                            },
                        )
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp, end = 12.dp)
                                .clip(RoundedCornerShape(8.dp)),
                        ) {
                            drawRect(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF00FF00),  // 绿色
                                        Color(0xFFFF0000),  // 红色
                                        Color(0xFFFF0000),  // 红色
                                        Color(0xFFFF00FF)   // 紫色
                                    ),
                                    start = Offset(size.width * 0.5f, size.height * 0.25f),
                                    end = Offset(size.width * 0.5f, size.height * 0.85f),
                                    tileMode = androidx.compose.ui.graphics.TileMode.Clamp
                                )
                            )
                        }
                    }
                    // Cyan
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(vertical = 4.dp)
                            .clickable {
                                // 点击整行时切换选中状态
                                val isChecked = selectedValue != MusicEffectColorEnum.Cyan
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.Cyan
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Checkbox(
                            checked = selectedValue == MusicEffectColorEnum.Cyan,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.Cyan
                                }
                            }
                        )
                        Text(
                            modifier = Modifier.widthIn(min = with(density) { maxTextWidth.toDp() }),
                            text = "Cyan",
                            onTextLayout = { layoutResult: TextLayoutResult ->
                                cyanTextWidth = layoutResult.size.width
                            },
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp, end = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Cyan),
                        ) {}
                    }
                    // 主题色
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(vertical = 4.dp)
                            .clickable {
                                // 点击整行时切换选中状态
                                val isChecked = selectedValue != MusicEffectColorEnum.FollowTheme
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.FollowTheme
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Checkbox(
                            checked = selectedValue == MusicEffectColorEnum.FollowTheme,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedValue = MusicEffectColorEnum.FollowTheme
                                }
                            }
                        )
                        Text(
                            modifier = Modifier.widthIn(min = with(density) { maxTextWidth.toDp() }),
                            text = "主题色",
                            onTextLayout = { layoutResult: TextLayoutResult ->
                                themeTextWidth = layoutResult.size.width
                            },
                        )
                        if (themeValue == ThemeEnum.Normal) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 12.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(primaryLight),
                            ) {}
                        } else {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, end = 12.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                painter = painterResource(themeMap[themeValue]!!.image),
                                contentDescription = themeMap[themeValue]!!.name,
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
            text = { Text("请选择配色。") },
            confirmButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text("确定")
                }
            }
        )
    }

}
