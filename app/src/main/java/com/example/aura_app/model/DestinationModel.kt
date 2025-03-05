package com.example.aura_app.model

import android.os.Parcel
import android.os.Parcelable

data class DestinationModel(
    var destImageUrl: String = "",
    var destId: String = "",
    var destName: String ="",
    var destDetail :String = "",
    var destCost :String ="",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(destImageUrl)
        parcel.writeString(destId)
        parcel.writeString(destName)
        parcel.writeString(destDetail)
        parcel.writeString(destCost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DestinationModel> {
        override fun createFromParcel(parcel: Parcel): DestinationModel {
            return DestinationModel(parcel)
        }

        override fun newArray(size: Int): Array<DestinationModel?> {
            return arrayOfNulls(size)
        }
    }

}