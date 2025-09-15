package com.crimson.app.heartsteel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform