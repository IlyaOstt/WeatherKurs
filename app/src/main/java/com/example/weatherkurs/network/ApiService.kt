package com.example.weatherkurs.network
import com.example.weatherkurs.model.*
import retrofit2.http.*
import com.example.weatherkurs.BuildConfig

interface ApiService {
    @GET("https://api.api-ninjas.com/v1/geocoding")
    suspend fun searchCity(
        @Query("city") cityName: String,
        @Header("X-Api-Key") apiKey: String = BuildConfig.API_KEY
    ): List<GeocodingResponse>

    @GET("https://api.open-meteo.com/v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") current: Boolean = true,
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("forecast_days") days: Int = 7,
        @Query("timezone") tz: String = "auto"
    ): WeatherResponse
}