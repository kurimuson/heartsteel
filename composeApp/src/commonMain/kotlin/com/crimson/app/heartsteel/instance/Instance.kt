package com.crimson.app.heartsteel.instance

import com.crimson.app.heartsteel.service.AppearanceService
import com.crimson.app.heartsteel.service.ListPlayerService
import com.crimson.app.heartsteel.service.MixerPlayerService
import com.crimson.app.heartsteel.service.StorageService

object Instance {

    val appearanceService: AppearanceService = AppearanceService()
    val mixerPlayerService: MixerPlayerService = MixerPlayerService()
    val listPlayerService: ListPlayerService = ListPlayerService()
    val storageService: StorageService = StorageService()

}