package com.example.weatherkurs.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.weatherkurs.model.*
import com.example.weatherkurs.network.RetrofitClient
import com.example.weatherkurs.utils.PreferencesManager
import kotlinx.coroutines.*

class SearchViewModel(private val prefs: PreferencesManager) : ViewModel() {
    var searchResult = mutableStateOf<List<GeocodingResponse>>(emptyList())
    private var searchJob: Job? = null

    fun onQueryChanged(query: String) {
        searchJob?.cancel()
        if (query.length < 2) {
            searchResult.value = emptyList()
            return
        }

        searchJob = viewModelScope.launch {
            delay(600)
            try {
                searchResult.value = RetrofitClient.api.searchCity(query)
            } catch (e: Exception) { }
        }
    }

    fun saveCity(city: GeocodingResponse) {
        prefs.saveCity(SavedCity(city.name, city.lat, city.lon))
    }
}