package com.thelegacycoder.instagramclone

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Aditya on 004, 4 Apr, 2018.
 */
data class User(val username: String = "", val password: String = "", val age: Int = 0, val location: String = "", val fullName: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(username)
        p0.writeString(password)
        p0.writeInt(age)
        p0.writeString(location)
        p0.writeString(fullName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return emptyArray()
        }
    }
}