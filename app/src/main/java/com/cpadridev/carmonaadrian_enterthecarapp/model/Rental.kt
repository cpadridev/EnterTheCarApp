package com.cpadridev.carmonaadrian_enterthecarapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rental (@SerializedName("id") @Expose var id: Int?,
                   @SerializedName("name") @Expose var name: String,
                   @SerializedName("vehicle") @Expose var vehicle: String,
                   @SerializedName("days") @Expose var days: Int,
                   @SerializedName("price") @Expose var price: Int)