package com.sunday.androidfliterchallenge.data.entity

import android.os.Parcel
import android.os.Parcelable

class Filter() : Parcelable {
    var id : String? = ""
    var avatar : String? = ""
    var fullName : String? = ""
    var createdAt : String? = ""
    var gender : String? = ""
    var colors : List<String>? = null
    var countries : List<String>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        fullName = parcel.readString()
        createdAt = parcel.readString()
        gender = parcel.readString()
        colors = parcel.createStringArrayList()
        countries = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(fullName)
        parcel.writeString(createdAt)
        parcel.writeString(gender)
        parcel.writeStringList(colors)
        parcel.writeStringList(countries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Filter> {
        override fun createFromParcel(parcel: Parcel): Filter {
            return Filter(parcel)
        }

        override fun newArray(size: Int): Array<Filter?> {
            return arrayOfNulls(size)
        }
    }


}