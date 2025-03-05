    package com.example.aura_app.model

    import android.os.Parcel
    import android.os.Parcelable

    data class TripModel(
        var id: String = "",  // Unique ID for each trip
        var detail: String = "",
        var date: String = "",
        var userId: String = ""
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: ""
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(detail)
            parcel.writeString(date)
            parcel.writeString(userId)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<TripModel> {
            override fun createFromParcel(parcel: Parcel): TripModel = TripModel(parcel)
            override fun newArray(size: Int): Array<TripModel?> = arrayOfNulls(size)
        }
    }
