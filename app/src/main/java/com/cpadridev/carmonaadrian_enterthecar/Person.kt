package com.cpadridev.carmonaadrian_enterthecar

import android.os.Parcel
import android.os.Parcelable
import android.widget.CheckBox

/**
@author: Adrian Carmona
 */
data class Person(val name: String, val surnames: String, val vehicleType: String, val fuelType: String?, val gps: Boolean, val days: String, val totalPrice: String) :
    Parcelable {
    companion object CREATOR: Parcelable.Creator<Person> {
        override fun createFromParcel(`in`: Parcel): Person {
            return Person(`in`)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

    constructor(`in`: Parcel) : this(`in`.readString()!!, `in`.readString()!!, `in`.readString()!!, `in`.readString(), `in`.readInt() != 0,
        `in`.readString()!!, `in`.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flag: Int) {
        out.writeString(name)
        out.writeString(surnames)
        out.writeString(vehicleType)
        out.writeString(fuelType)
        out.writeInt(if(gps) 1 else 0)
        out.writeString(days)
        out.writeString(totalPrice)
    }
}
