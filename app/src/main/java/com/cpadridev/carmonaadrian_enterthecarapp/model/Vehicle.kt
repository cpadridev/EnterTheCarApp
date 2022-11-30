package com.cpadridev.carmonaadrian_enterthecarapp.model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.cpadridev.carmonaadrian_enterthecarapp.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Vehicle (@SerializedName("id") @Expose var id: Int?,
                    @SerializedName("type") @Expose var type: String,
                    @SerializedName("price") @Expose var price: Int,) {

    override fun toString(): String {
        return when (type) {
            "tourism" -> Resources.getSystem().getString(R.string.tourism)
            "motorbike" -> Resources.getSystem().getString(R.string.motorbike)
            "scooter" -> Resources.getSystem().getString(R.string.scooter)
            else -> "$type $price ${Resources.getSystem().getString(R.string.template_price_day)}"
        }
    }
}
