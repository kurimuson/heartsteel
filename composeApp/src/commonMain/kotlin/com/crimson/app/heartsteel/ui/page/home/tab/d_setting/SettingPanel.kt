package com.crimson.app.heartsteel.ui.page.home.tab.d_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item.DarkModeItemPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item.MusicEffectItemPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item.MusicVernaItemPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item.RoundWatchItemPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.item.ThemeItemPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SettingPanel(
    callbackHeader: (header: @Composable () -> Unit) -> Unit = {}
) {

    val scrollState = rememberScrollState()

    callbackHeader {
        HeaderPanel(text = "设置")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (AppearanceStateStore.isRoundWatch()) {
                    Modifier.padding(horizontal = 56.dp)
                } else {
                    Modifier
                }
            )
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DarkModeItemPanel()
            ThemeItemPanel()
            if (Instance.appearanceService.isWatch()) {
                RoundWatchItemPanel()
            }
            if (!Instance.appearanceService.isWatch()) {
                MusicVernaItemPanel()
                MusicEffectItemPanel()
            }
        }
    }

}