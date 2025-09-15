package com.crimson.app.heartsteel.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.MixerPanel
import com.crimson.app.heartsteel.ui.page.home.tab.b_list.ListPanel
import com.crimson.app.heartsteel.ui.page.home.tab.c_discover.DiscoverPanel
import com.crimson.app.heartsteel.ui.page.home.tab.d_setting.SettingPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class TabInfo(
    val tab: Int,
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val contentDescription: String,
) {
    MIXER(
        tab = 0,
        label = "混音",
        unselectedIcon = Icons.Outlined.Album,
        selectedIcon = Icons.Filled.Album,
        contentDescription = "混音器",
    ),
    LIST(
        tab = 1,
        label = "列表",
        unselectedIcon = Icons.Outlined.LibraryMusic,
        selectedIcon = Icons.Filled.LibraryMusic,
        contentDescription = "播放列表",
    ),
    DISCOVER(
        tab = 2,
        label = "发现",
        unselectedIcon = Icons.Outlined.StarBorder,
        selectedIcon = Icons.Filled.Star,
        contentDescription = "发现",
    ),
    SETTING(
        tab = 3,
        label = "设置",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        contentDescription = "设置",
    ),
}

@Composable
@Preview
fun HomePagePanel(
    navController: NavHostController? = null,
    tab: String = "mixer",
) {

    val startDestination = when (tab) {
        "mixer" -> TabInfo.MIXER
        "list" -> TabInfo.LIST
        "discover" -> TabInfo.DISCOVER
        "setting" -> TabInfo.SETTING
        else -> TabInfo.MIXER
    }
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    var header by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    Scaffold(
        topBar = {
            if (header != null) {
                header!!()
            } else {
                HeaderPanel()
            }
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                if (TabInfo.MIXER.tab == selectedDestination) {
                    MixerPanel { h -> header = h }
                }
                if (TabInfo.LIST.tab == selectedDestination) {
                    ListPanel { h -> header = h }
                }
                if (TabInfo.DISCOVER.tab == selectedDestination) {
                    DiscoverPanel(
                        onNavigateToDetail = { name ->
                            navController?.navigate("detail/$name")
                        }
                    ) { h -> header = h }
                }
                if (TabInfo.SETTING.tab == selectedDestination) {
                    SettingPanel { h -> header = h }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                NavigationBar(
                    modifier = if (AppearanceStateStore.isRoundWatch()) {
                        Modifier.padding(
                            start = 120.dp,
                            end = 120.dp,
                            bottom = 20.dp,
                        )
                    } else {
                        Modifier
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.surfaceContainer,
                    windowInsets = NavigationBarDefaults.windowInsets,
                ) {
                    TabInfo.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                selectedDestination = index
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedDestination == index) {
                                        destination.selectedIcon
                                    } else {
                                        destination.unselectedIcon
                                    },
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    )

}