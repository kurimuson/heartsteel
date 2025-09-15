package com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.store.AppearanceStateStore
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DarkModeItemPanel() {

    var darkModeFollowBySystem by remember { mutableStateOf(AppearanceStateStore.value.darkModeFollowBySystem) }
    var darkMode by remember { mutableStateOf(AppearanceStateStore.value.darkMode) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.DarkMode,
                contentDescription = null
            )
            if (AppearanceStateStore.supportDarkMode) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = if (darkModeFollowBySystem) {
                        "深色模式：跟随系统"
                    } else {
                        "深色模式：自定义"
                    },
                    fontSize = 16.sp
                )
                Checkbox(
                    checked = darkModeFollowBySystem,
                    onCheckedChange = { isChecked ->
                        darkModeFollowBySystem = isChecked
                        AppearanceStateStore.setDarkModeFollowBySystem(isChecked)
                    }
                )
            } else {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "深色模式",
                    fontSize = 16.sp
                )
            }
            Switch(
                enabled = !darkModeFollowBySystem,
                checked = darkMode,
                onCheckedChange = { isChecked ->
                    darkMode = isChecked
                    AppearanceStateStore.setDarkMode(isChecked)
                }
            )
        }
    }

}
