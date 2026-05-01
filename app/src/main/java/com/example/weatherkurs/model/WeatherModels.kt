package com.example.weatherkurs.model

import com.google.gson.annotations.SerializedName

data class HourlyData(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weather_code: List<Int>
)

data class WeatherResponse(
    @SerializedName("current_weather")
    val current: CurrentWeather?,
    val hourly: HourlyData
)

data class CurrentWeather(
    @SerializedName("temperature")
    val temperature_2m: Double,
    @SerializedName("weathercode")
    val weather_code: Int
)