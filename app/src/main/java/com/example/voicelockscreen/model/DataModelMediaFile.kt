package com.example.voicelockscreen.model

import android.os.Parcel
import android.os.Parcelable

class DataModelMediaFile(
    private val index: String?,
    private val title: String?,
    val name: String?,
    val size: String?,
    val duration: String?,
    val path: String?,
    private val dateAdded: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(index)
        parcel.writeString(title)
        parcel.writeString(name)
        parcel.writeString(size)
        parcel.writeString(duration)
        parcel.writeString(path)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModelMediaFile> {
        override fun createFromParcel(parcel: Parcel): DataModelMediaFile {
            return DataModelMediaFile(parcel)
        }

        override fun newArray(size: Int): Array<DataModelMediaFile?> {
            return arrayOfNulls(size)
        }
    }
}