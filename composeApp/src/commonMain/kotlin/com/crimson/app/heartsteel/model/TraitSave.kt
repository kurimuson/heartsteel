package com.crimson.app.heartsteel.model

expect class TraitSave(
    uid: String,
    name: String,
    image: String,
    step: String,
    trackList: List<String>,
    trackMap: Map<String, List<TraitEnum>>,
) {
    internal val uid: String
    internal var name: String
    internal var image: String
    internal val step: String
    internal val trackList: List<String>
    internal val trackMap: Map<String, List<TraitEnum>>
}
