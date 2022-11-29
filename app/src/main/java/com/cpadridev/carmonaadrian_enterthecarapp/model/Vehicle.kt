package com.cpadridev.carmonaadrian_enterthecarapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Vehicle(@SerializedName("id") @Expose var id: Int?,
                   @SerializedName("type") @Expose var type: String,
                   @SerializedName("price") @Expose var price: Int,)