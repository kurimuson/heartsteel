package com.crimson.app.heartsteel.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimson.app.heartsteel.store.AppearanceStateStore
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HeaderPanel(
    left: @Composable () -> Unit = { },
    text: String = "Heartsteel",
    right: @Composable () -> Unit = { },
) {

    val modifier = if (AppearanceStateStore.isRoundWatch()) {
        Modifier
            .fillMaxWidth()
            .padding(
                start = 135.dp,
                end = 135.dp,
                top = 20.dp,
            )
    } else {
        Modifier.fillMaxWidth()
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                left()
                Text(
                    modifier = Modifier.weight(1f),
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                right()
            }
        }
    )
}