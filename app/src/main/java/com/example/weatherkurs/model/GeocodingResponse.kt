package com.example.weatherkurs.model

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    val name: String,

    @SerializedName("latitude")
    val lat: Double,

    @SerializedName("longitude")
    val lon: Double,

    val country: String? = null
)