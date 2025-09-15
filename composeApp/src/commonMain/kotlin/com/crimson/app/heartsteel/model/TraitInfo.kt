package com.crimson.app.heartsteel.model

import org.jetbrains.compose.resources.DrawableResource

data class TraitInfo(
    val key: TraitEnum,
    val name: String,
    val href: String,
    val icon: DrawableResource?,
    val panel: DrawableResource,
    val musicTrackMap: Map<String, Map<String, String>>,
)