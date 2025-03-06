package com.example.aura_app.model

import android.os.Parcel
import android.os.Parcelable

data class ProfileModel(
    var profileImageUrl: String = "",
    var coverImageUrl: String = "",
    var highlights: List<String> = listOf(),
    var posts: Int = 0,
    var userId: String = "",
    var username: String = "" // Add username
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(profileImageUrl)

        parcel.writeString(coverImageUrl)
        parcel.writeStringList(highlights)
        parcel.writeInt(posts)
        parcel.writeString(userId)
        parcel.writeString(username)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ProfileModel> {
        override fun createFromParcel(parcel: Parcel): ProfileModel = ProfileModel(parcel)
        override fun newArray(size: Int): Array<ProfileModel?> = arrayOfNulls(size)
    }
}