package com.crimson.app.heartsteel.ui.page.home.tab.c_discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.common.traitInfoMap
import com.crimson.app.heartsteel.common.traitItemListGroupMap
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DiscoverPanel(
    onNavigateToDetail: (String) -> Unit = {},
    callbackHeader: (header: @Composable () -> Unit) -> Unit = {}
) {

    val scrollState = rememberScrollState()
    val titleMap = mapOf(
        "3" to "三音轨组",
        "2" to "双音轨组",
        "1" to "单音轨组",
    )

    callbackHeader {
        HeaderPanel(text = "发现")
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
            for (traitItemList in traitItemListGroupMap) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    text = titleMap[traitItemList.key]!!,
                )
                for (key in traitItemList.value) {
                    val trait = traitInfoMap[key]!!
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onClick = {
                            onNavigateToDetail(trait.href)
                        },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(46.dp)
                                    .wrapContentHeight()
                                    .padding(start = 4.dp),
                                painter = painterResource(trait.panel),
                                contentDescription = trait.name,
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center,
                            )
                            Text(
                                text = trait.name,
                                textAlign = TextAlign.Center,
                            )
                            IconButton(
                                onClick = {
                                    onNavigateToDetail(trait.href)
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    imageVector = Icons.Outlined.ChevronRight,
                                    contentDescription = "进入"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}