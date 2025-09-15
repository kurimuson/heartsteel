package com.crimson.app.heartsteel.model

import kotlinx.serialization.Serializable

@Serializable
class PlayTime {

    var currentTimeMillis: Int = 0
    var allTimeMillis: Int = 0

    constructor(
        currentTimeMillis: Int = 0,
        allTimeMillis: Int = 0
    ) {
        this.currentTimeMillis = currentTimeMillis
        this.allTimeMillis = allTimeMillis
    }

}