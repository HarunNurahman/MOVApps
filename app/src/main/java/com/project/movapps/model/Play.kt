package com.project.movapps.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class Play(
    var nama: String? = "",
    var url: String? = "",

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Play> {
        override fun createFromParcel(parcel: Parcel): Play {
            return Play(parcel)
        }

        override fun newArray(size: Int): Array<Play?> {
            return arrayOfNulls(size)
        }
    }
}