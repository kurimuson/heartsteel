package com.crimson.app.heartsteel.ui.page.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.crimson.app.heartsteel.common.trailDetailMap
import com.crimson.app.heartsteel.common.traitChineseNameMap
import com.crimson.app.heartsteel.common.traitHrefToEnum
import com.crimson.app.heartsteel.common.traitInfoMap
import com.crimson.app.heartsteel.store.AppearanceStateStore
import com.crimson.app.heartsteel.ui.HeaderPanel
import com.crimson.app.heartsteel.ui.component.TraitIconComponent
import heartsteel.composeapp.generated.resources.Res
import heartsteel.composeapp.generated.resources.TFT10_Champion_Ahri
import heartsteel.composeapp.generated.resources.TFT10_Champion_Akali
import heartsteel.composeapp.generated.resources.TFT10_Champion_Akali_TrueDamage
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Breakout
import heartsteel.composeapp.generated.resources.Trait_Icon_10_KDA
import heartsteel.composeapp.generated.resources.Trait_Icon_4_Executioner
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DetailPagePanel(
    navController: NavHostController? = null,
    name: String = "kda",
) {

    val scrollState = rememberScrollState()
    val traitInfo = remember { traitInfoMap[traitHrefToEnum[name]]!! }
    val trailDetailList = remember { trailDetailMap[traitInfo.key]!! }
    val traitNameMap = remember { traitChineseNameMap }

    Scaffold(
        topBar = {
            HeaderPanel(
                left = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Outlined.ChevronLeft,
                            contentDescription = "进入"
                        )
                    }
                },
                text = traitInfo.name
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .then(
                        if (AppearanceStateStore.isRoundWatch()) {
                            Modifier.padding(
                                start = 56.dp,
                                end = 56.dp,
                                top = 20.dp,
                                bottom = 104.dp
                            )
                        } else {
                            Modifier.padding(16.dp)
                        }
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                for (traitDetail in trailDetailList) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1.75f),
                                painter = painterResource(traitDetail.image),
                                contentDescription = "kda",
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center,
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 24.dp)
                                    .align(Alignment.CenterStart),
                            ) {
                                for (tip in traitDetail.tipList) {
                                    InputChip(
                                        onClick = { },
                                        label = { Text(traitNameMap[tip.key]!!) },
                                        selected = true,
                                        avatar = {
                                            TraitIconComponent(tip.icon)
                                        },
                                        colors = InputChipDefaults.inputChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                                                alpha = 0.6f
                                            ),
                                        ),
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