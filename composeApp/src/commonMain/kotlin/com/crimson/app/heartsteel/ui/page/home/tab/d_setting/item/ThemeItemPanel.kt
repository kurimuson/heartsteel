package com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.common.themeMap
import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.theme.normal.primaryLight
import com.crimson.app.heartsteel.ui.dialog.ThemeSelectorDialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ThemeItemPanel() {

    var showSelectorDialog by remember { mutableStateOf(false) }
    var selectedThemeKey by remember { mutableStateOf(AppearanceStateStore.value.theme) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Palette,
                contentDescription = null
            )
            Text(
                text = "主题",
                fontSize = 16.sp,
                maxLines = 1,
            )
            if (selectedThemeKey == ThemeEnum.Normal) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(primaryLight),
                ) {}
            } else {
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(themeMap[selectedThemeKey]!!.image),
                    contentDescription = themeMap[selectedThemeKey]!!.name,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center,
                )
            }
            IconButton(
                onClick = {
                    showSelectorDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        }
    }

    when {
        showSelectorDialog -> {
            ThemeSelectorDialog(
                default = selectedThemeKey,
                onDismissRequest = {
                    showSelectorDialog = false
                },
                onConfirmation = { value ->
                    showSelectorDialog = false
                    selectedThemeKey = value
                    AppearanceStateStore.setTheme(value)
                }
            )
        }
    }

}
