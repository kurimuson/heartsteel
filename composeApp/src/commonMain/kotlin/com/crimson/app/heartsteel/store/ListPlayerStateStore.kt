package com.crimson.app.heartsteel.store

import com.crimson.app.heartsteel.common.json
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitSave

object ListPlayerStateStore {

    internal val traitSaveList = mutableListOf<TraitSave>()

    internal var isPlaying = false
    internal var currentPlayUid = ""
    internal var currentPlayTime = PlayTime(0, 0)
    internal var isControlEnable = false

    private lateinit var u1: String
    private lateinit var u2: String
    private lateinit var u3: String
    private lateinit var u4: String

    fun loadTraitSaveList(onResult: (MutableList<TraitSave>) -> Unit = {}) {
        // 这两个方法一定要返回新的list，防止浅拷贝污染
        Instance.storageService.loadData("trait_save_list") { result ->
            try {
                val list = json.decodeFromString<MutableList<TraitSave>>(result)
                traitSaveList.clear()
                traitSaveList.addAll(list)
                Instance.listPlayerService.updatePlayList(mutableListOf<TraitSave>().apply { addAll(list) }) // 同步至播放器
                onResult(list)
            } catch (e: Exception) {
                val list = mutableListOf<TraitSave>()
                Instance.storageService.saveData(
                    "trait_save_list",
                    json.encodeToString(list)
                )
                Instance.listPlayerService.updatePlayList(mutableListOf<TraitSave>().apply { addAll(list) }) // 同步至播放器
                onResult(list)
            }
        }
    }

    fun saveTraitSaveList(list: MutableList<TraitSave>) {
        traitSaveList.clear()
        traitSaveList.addAll(list)
        Instance.storageService.saveData("trait_save_list", json.encodeToString(list))
        Instance.listPlayerService.updatePlayList(mutableListOf<TraitSave>().apply { addAll(list) }) // 同步至播放器
    }

    private val playStateCallback = { result: Boolean ->
        isPlaying = result
    }

    private val playUidUpdateCallback = { result: String ->
        currentPlayUid = result
    }

    private val playTimeCallback = { result: PlayTime ->
        currentPlayTime = result
    }

    private val controlEnableCallback = { result: Boolean ->
        isControlEnable = result
    }

    fun addPlayerEventListener() {
        u1 = Instance.listPlayerService.addPlayStateCallback(playStateCallback)
        u2 = Instance.listPlayerService.addPlayUidUpdateCallback(playUidUpdateCallback)
        u3 = Instance.listPlayerService.addPlayTimeCallback(playTimeCallback)
        u4 = Instance.listPlayerService.addControlEnableCallback(controlEnableCallback)
    }

    fun removePlayerEventListener() {
        Instance.listPlayerService.removePlayStateCallback(u1)
        Instance.listPlayerService.removePlayUidUpdateCallback(u2)
        Instance.listPlayerService.removePlayTimeCallback(u3)
        Instance.listPlayerService.removeControlEnableCallback(u4)
    }

    fun reset() {
        isPlaying = false
        currentPlayUid = ""
        currentPlayTime = PlayTime(0, 0)
        isControlEnable = false
    }

}