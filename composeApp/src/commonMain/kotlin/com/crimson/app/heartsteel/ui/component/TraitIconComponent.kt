package com.crimson.app.heartsteel.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.InputChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import com.crimson.app.heartsteel.store.AppearanceStateStore
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TraitIconComponent(icon: DrawableResource?) {

    when {
        icon != null ->
            Image(
                modifier = Modifier.size(InputChipDefaults.AvatarSize),
                painter = painterResource(icon),
                contentDescription = "TraitIcon",
                colorFilter = if (AppearanceStateStore.currentAppIsInDarkMode) {
                    null
                } else {
                    ColorFilter.colorMatrix(
                        ColorMatrix(
                            floatArrayOf(
                                -1f, 0f, 0f, 0f, 255f, // 红色通道反转
                                0f, -1f, 0f, 0f, 255f, // 绿色通道反转
                                0f, 0f, -1f, 0f, 255f, // 蓝色通道反转
                                0f, 0f, 0f, 1f, 0f     // 透明度通道不变
                            )
                        )
                    )
                }
            )
    }

}