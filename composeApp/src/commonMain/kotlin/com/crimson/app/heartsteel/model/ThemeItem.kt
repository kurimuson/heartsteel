package com.crimson.app.heartsteel.model

import org.jetbrains.compose.resources.DrawableResource

data class ThemeItem(
    internal val key: ThemeEnum,
    internal val name: String,
    internal val image: DrawableResource
)
