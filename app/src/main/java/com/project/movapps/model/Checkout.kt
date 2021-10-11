package com.project.movapps.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class Checkout(
    var kursi: String? = "",
    var harga: String? = "",

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kursi)
        parcel.writeString(harga)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Checkout> {
        override fun createFromParcel(parcel: Parcel): Checkout {
            return Checkout(parcel)
        }

        override fun newArray(size: Int): Array<Checkout?> {
            return arrayOfNulls(size)
        }
    }
}