package com.crimson.app.heartsteel.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.crimson.app.heartsteel.common.traitInfoMap
import com.crimson.app.heartsteel.model.TraitEnum

@Composable
fun TraitChipComponent(
    key: TraitEnum,
    onClose: (() -> Unit)? = null
) {

    val traitInfo = traitInfoMap[key]!!

    InputChip(
        onClick = { },
        label = { Text(traitInfo.name) },
        selected = true,
        avatar = {
            TraitIconComponent(traitInfo.icon)
        },
        trailingIcon = if (onClose != null) {
            {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(InputChipDefaults.AvatarSize)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "关闭",
                    )
                }
            }
        } else {
            null
        },
    )

}
