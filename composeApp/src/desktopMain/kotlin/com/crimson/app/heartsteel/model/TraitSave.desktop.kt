package com.crimson.app.heartsteel.model

import kotlinx.serialization.Serializable

@Serializable
actual class TraitSave actual constructor(
    actual val uid: String,
    actual val name: String,
    actual val image: String,
    actual val step: String,
    actual val trackList: List<String>,
    actual val trackMap: Map<String, List<TraitEnum>>
)
