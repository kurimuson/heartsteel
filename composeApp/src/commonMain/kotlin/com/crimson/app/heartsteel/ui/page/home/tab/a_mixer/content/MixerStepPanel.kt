package com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.model.TraitSave
import com.crimson.app.heartsteel.store.ListPlayerStateStore
import com.crimson.app.heartsteel.store.MixerPlayerStateStore
import com.crimson.app.heartsteel.ui.dialog.TraitListSaverDialog
import com.crimson.app.heartsteel.ui.page.home.tab.a_mixer.content.card.MixerEachCard
import com.crimson.app.heartsteel.util.CommonUtil
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun MixerStepPanel(
    step: String = "1",
    onTrackListChange: (result: List<String>) -> Unit = {},
) {

    val scrollState = rememberScrollState()

    var showAlertDialog by remember { mutableStateOf(false) }
    var showSaverDialog by remember { mutableStateOf(false) }

    val onTraitsSelected: (String, List<TraitEnum>) -> Unit = { type, result ->
        onTrackListChange(MixerPlayerStateStore.getTrackList(step))
    }

    val saveDialogOpen: () -> Unit = {
        if (MixerPlayerStateStore.getTrackList(step).isEmpty()) {
            showAlertDialog = true
        } else {
            showSaverDialog = true
        }
    }

    val saveToStorage: (name: String, image: String) -> Unit = { name, image ->
        ListPlayerStateStore.loadTraitSaveList(onResult = { list ->
            val data = TraitSave(
                uid = CommonUtil.generateCharMixedExt(16) + "___" + Clock.System.now().toEpochMilliseconds(),
                name = name,
                image = image,
                step = step,
                trackList = MixerPlayerStateStore.getTrackList(step),
                trackMap = MixerPlayerStateStore.selectedTraitListMap[step]!!
            )
            list.add(data)
            ListPlayerStateStore.saveTraitSaveList(list)
        })
    }

    LaunchedEffect(Unit) {
        if (MixerPlayerStateStore.getTrackList(step).isNotEmpty()) {
            onTrackListChange(MixerPlayerStateStore.getTrackList(step))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(8.dp),
    ) {
        MixerEachCard(step, "ry") { result -> onTraitsSelected("ry", result) }
        MixerEachCard(step, "tr") { result -> onTraitsSelected("tr", result) }
        MixerEachCard(step, "bg") { result -> onTraitsSelected("bg", result) }
        Button(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp),
            onClick = saveDialogOpen
        ) {
            Text("保存")
        }
    }

    when {
        showAlertDialog -> {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                text = { Text("请选择音轨。") },
                confirmButton = {
                    TextButton(
                        onClick = { showAlertDialog = false }
                    ) {
                        Text("确定")
                    }
                }
            )
        }
    }

    when {
        showSaverDialog -> {
            TraitListSaverDialog(
                onDismissRequest = {
                    showSaverDialog = false
                },
                onConfirmation = { name, image ->
                    showSaverDialog = false
                    saveToStorage(name, image)
                }
            )
        }
    }

}
