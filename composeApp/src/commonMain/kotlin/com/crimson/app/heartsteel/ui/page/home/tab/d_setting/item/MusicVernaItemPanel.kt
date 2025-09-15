package com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlipToFront
import androidx.compose.material3.Card
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
fun MusicVernaItemPanel() {

    var showMusicVerna by remember { mutableStateOf(AppearanceStateStore.value.showMusicVerna) }

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
                imageVector = Icons.Filled.FlipToFront,
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f),
                text =  "高亮边框",
                fontSize = 16.sp
            )
            Switch(
                enabled = true,
                checked = showMusicVerna,
                onCheckedChange = { isChecked ->
                    showMusicVerna = isChecked
                    AppearanceStateStore.setShowMusicVerna(isChecked)
                }
            )
        }
    }

}