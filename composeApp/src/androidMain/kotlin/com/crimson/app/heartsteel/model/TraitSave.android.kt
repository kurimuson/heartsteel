package com.crimson.app.heartsteel.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
actual class TraitSave actual constructor(
    actual val uid: String,
    actual var name: String,
    actual var image: String,
    actual val step: String,
    actual val trackList: List<String>,
    actual val trackMap: Map<String, List<TraitEnum>>
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(step)
        parcel.writeStringList(trackList)
        parcel.writeInt(trackMap.size)
        for ((key, valueList) in trackMap) {
            parcel.writeString(key)
            parcel.writeInt(valueList.size)
            valueList.forEach { parcel.writeString(it.name) }
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TraitSave> {
        override fun createFromParcel(parcel: Parcel): TraitSave {
            val uid = parcel.readString()!!
            val name = parcel.readString()!!
            val image = parcel.readString()!!
            val step = parcel.readString()!!
            val trackList = parcel.createStringArrayList()!!

            val mapSize = parcel.readInt()
            val trackMap = mutableMapOf<String, List<TraitEnum>>()
            repeat(mapSize) {
                val key = parcel.readString()!!
                val listSize = parcel.readInt()
                val valueList = mutableListOf<TraitEnum>()
                repeat(listSize) {
                    valueList.add(TraitEnum.valueOf(parcel.readString()!!))
                }
                trackMap[key] = valueList
            }
            return TraitSave(uid, name, image, step, trackList, trackMap)
        }

        override fun newArray(size: Int): Array<TraitSave?> {
            return arrayOfNulls(size)
        }
    }
}