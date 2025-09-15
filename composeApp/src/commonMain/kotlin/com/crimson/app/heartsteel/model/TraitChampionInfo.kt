package com.crimson.app.heartsteel.model

import org.jetbrains.compose.resources.DrawableResource

data class TraitTip(
    val key: TraitEnum,
    val icon: DrawableResource,
)

data class TraitChampionInfo(
    val level: Int,
    val image: DrawableResource,
    val tipList: List<TraitTip>,
)