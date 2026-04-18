package com.example.weatherkurs.utils
import android.content.Context
import com.example.weatherkurs.model.SavedCity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveCity(city: SavedCity) {
        val cities = getCities().toMutableList()
        if (!cities.any { it.name == city.name }) {
            cities.add(city)
            prefs.edit().putString("saved_cities", gson.toJson(cities)).apply()
        }
    }

    fun getCities(): List<SavedCity> {
        val json = prefs.getString("saved_cities", null) ?: return emptyList()
        return gson.fromJson(json, object : TypeToken<List<SavedCity>>() {}.type)
    }

    fun deleteCity(cityName: String) {
        val cities = getCities().toMutableList()
        cities.removeAll { it.name == cityName }
        prefs.edit().putString("saved_cities", gson.toJson(cities)).apply()
    }
}