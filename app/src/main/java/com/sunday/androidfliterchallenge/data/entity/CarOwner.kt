package com.sunday.androidfliterchallenge.data.entity

import android.os.Parcel
import android.os.Parcelable

class CarOwner() : Parcelable {

    var firstName : String? = ""
    var lastName : String? = ""
    var email : String? = ""
    var country : String? = ""
    var carModel : String? = ""
    var carModelYear : String? = ""
    var carColor : String? = ""
    var gender : String? = ""
    var jobTitle : String? = ""
    var bio : String? = ""

    constructor(parcel: Parcel) : this() {
        firstName = parcel.readString()
        lastName = parcel.readString()
        email = parcel.readString()
        country = parcel.readString()
        carModel = parcel.readString()
        carModelYear = parcel.readString()
        carColor = parcel.readString()
        gender = parcel.readString()
        jobTitle = parcel.readString()
        bio = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(country)
        parcel.writeString(carModel)
        parcel.writeString(carModelYear)
        parcel.writeString(carColor)
        parcel.writeString(gender)
        parcel.writeString(jobTitle)
        parcel.writeString(bio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarOwner> {
        override fun createFromParcel(parcel: Parcel): CarOwner {
            return CarOwner(parcel)
        }

        override fun newArray(size: Int): Array<CarOwner?> {
            return arrayOfNulls(size)
        }
    }

}