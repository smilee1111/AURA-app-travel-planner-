package com.example.aura_app.model

import android.os.Parcel
import android.os.Parcelable

data class ItiineryModel(
    var id: String = "",  // Unique ID for each Itiinery
    var destination: String = "",
    var plan1: String = "",
    var plan2: String = "",
    var plan3: String = "",
    var plan4: String = "",
    var userId: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(destination)
        parcel.writeString(plan1)
        parcel.writeString(plan2)
        parcel.writeString(plan3)
        parcel.writeString(plan4)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ItiineryModel> {
        override fun createFromParcel(parcel: Parcel): ItiineryModel = ItiineryModel(parcel)
        override fun newArray(size: Int): Array<ItiineryModel?> = arrayOfNulls(size)
    }
}
