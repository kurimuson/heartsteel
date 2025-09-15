package com.crimson.app.heartsteel.service

import com.crimson.app.heartsteel.ContextHolder
import com.crimson.app.heartsteel.storage.StringDataStorage

actual class StorageService {

    private var storage = StringDataStorage(ContextHolder.getContext())

    actual fun saveData(key: String, data: String) {
        storage.saveData(key, data)
    }

    actual fun loadData(key: String, onResult: (String) -> Unit) {
        storage.loadData(key, onResult)
    }

}
