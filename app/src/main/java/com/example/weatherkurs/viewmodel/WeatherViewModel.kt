package com.example.weatherkurs.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherkurs.model.WeatherResponse
import com.example.weatherkurs.network.RetrofitClient
import com.example.weatherkurs.utils.PreferencesManager
import kotlinx.coroutines.*

data class CityState(val name: String, val temp: String, val iconCode: Int, val lat: Double, val lon: Double)

class WeatherViewModel(private val prefs: PreferencesManager) : ViewModel() {
    var citiesWeather = mutableStateOf<List<CityState>>(emptyList())
    var isLoading = mutableStateOf(false)
    var detailedWeather = mutableStateOf<WeatherResponse?>(null)

    fun loadMainList() {
        viewModelScope.launch {
            isLoading.value = true
            val results = prefs.getCities().map { city ->
                async(Dispatchers.IO) {
                    try {
                        val res = RetrofitClient.api.getWeather(city.lat, city.lon)
                        CityState(
                            name = city.name,
                            temp = "${res.current?.temperature_2m ?: 0.0}°C",
                            iconCode = res.current?.weather_code ?: 0,
                            lat = city.lat,
                            lon = city.lon
                        )
                    } catch (e: Exception) {
                        CityState(city.name, "?", 0, city.lat, city.lon)
                    }
                }
            }.awaitAll()
            citiesWeather.value = results
            isLoading.value = false
        }
    }

    fun loadDetails(lat: Double, lon: Double) {
        viewModelScope.launch {
            detailedWeather.value = null
            try {
                detailedWeather.value = RetrofitClient.api.getWeather(lat, lon)
            } catch (e: Exception) { }
        }
    }

    fun removeCity(cityName: String) {
        prefs.deleteCity(cityName)
        loadMainList()
    }
}