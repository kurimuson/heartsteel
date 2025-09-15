package com.crimson.app.heartsteel.service

expect class StorageService {

    constructor()

    fun saveData(key: String, data: String)

    fun loadData(key: String, onResult: (String) -> Unit)

}