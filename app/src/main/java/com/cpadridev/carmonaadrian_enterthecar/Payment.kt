package com.cpadridev.carmonaadrian_enterthecar

import android.os.Parcel
import android.os.Parcelable

data class Payment(val cardType: String, val cardNumber: Int, val expirationDate: String) : Parcelable {
    companion object CREATOR: Parcelable.Creator<Payment> {
        override fun createFromParcel(`in`: Parcel): Payment {
            return Payment(`in`)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }

    constructor(`in`: Parcel) : this(`in`.readString()!!, `in`.readInt(), `in`.readString()!!)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flag: Int) {
        out.writeString(cardType)
        out.writeString(cardNumber.toString())
        out.writeString(expirationDate)
    }
}