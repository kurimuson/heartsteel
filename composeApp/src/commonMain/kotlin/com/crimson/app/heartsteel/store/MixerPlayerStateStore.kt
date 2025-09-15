package com.crimson.app.heartsteel.store

import com.crimson.app.heartsteel.common.traitInfoMap
import com.crimson.app.heartsteel.instance.Instance
import com.crimson.app.heartsteel.model.PlayTime
import com.crimson.app.heartsteel.model.TraitEnum

object MixerPlayerStateStore {

    internal var selectedPanel = 0
    internal val selectedTraitListMap =
        mutableMapOf<String, MutableMap<String, MutableList<TraitEnum>>>(
            "1" to mutableMapOf(
                "ry" to mutableListOf(),
                "bg" to mutableListOf(),
                "tr" to mutableListOf(),
                "s" to mutableListOf(),
            ),
            "2" to mutableMapOf(
                "ry" to mutableListOf(),
                "bg" to mutableListOf(),
                "tr" to mutableListOf(),
                "s" to mutableListOf(),
            ),
        )

    fun getTrackList(step: String): List<String> {
        val list = mutableListOf<String>()
        val map = selectedTraitListMap[step]!!
        map.forEach { (type, traitsList) ->
            for (traits in traitsList) {
                val info = traitInfoMap[traits]!!
                list.add(info.musicTrackMap[step]!![type]!!)
            }
        }
        return list;
    }

    internal var isPlaying = false
    internal var currentPlayTime = PlayTime(0, 0)
    internal var isControlEnable = false

    private lateinit var u1: String
    private lateinit var u2: String
    private lateinit var u3: String

    private val playStateCallback = { result: Boolean ->
        isPlaying = result
    }

    private val playTimeCallback = { result: PlayTime ->
        currentPlayTime = result
    }

    private val controlEnableCallback = { result: Boolean ->
        isControlEnable = result
    }

    fun addPlayerEventListener() {
        u1 = Instance.mixerPlayerService.addPlayStateCallback(playStateCallback)
        u2 = Instance.mixerPlayerService.addPlayTimeCallback(playTimeCallback)
        u3 = Instance.mixerPlayerService.addControlEnableCallback(controlEnableCallback)
    }

    fun removePlayerEventListener() {
        Instance.mixerPlayerService.removePlayStateCallback(u1)
        Instance.mixerPlayerService.removePlayTimeCallback(u2)
        Instance.mixerPlayerService.removeControlEnableCallback(u3)
    }

    fun reset() {
        ListPlayerStateStore.isPlaying = false
        ListPlayerStateStore.currentPlayTime = PlayTime(0, 0)
        ListPlayerStateStore.isControlEnable = false
    }

}