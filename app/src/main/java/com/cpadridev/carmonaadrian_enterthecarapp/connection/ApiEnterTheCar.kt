package com.cpadridev.carmonaadrian_enterthecarapp.connection

import com.cpadridev.carmonaadrian_enterthecarapp.model.Rental
import com.cpadridev.carmonaadrian_enterthecarapp.model.Vehicle
import retrofit2.Call
import retrofit2.http.*

interface ApiEnterTheCar {
    @GET("vehicles")
    fun getVehicles(): Call<ArrayList<Vehicle>>

    @GET("rentals")
    fun getRentals(): Call<ArrayList<Rental>>

    @FormUrlEncoded
    @POST("rentals")
    fun saveRent(@Field("name") name: String,
                 @Field("vehicle") vehicle: String,
                 @Field("days") days: Int,
                 @Field("price") price: Int): Call<Rental>
}