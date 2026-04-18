package com.example.weatherkurs.utils
object WeatherMapper {
    fun getIconUrl(code: Int): String {
        val icon = when (code) {
            0 -> "01d"
            1, 2 -> "02d"
            3 -> "03d"
            45, 48 -> "50d"
            51, 53, 55, 61, 63, 65 -> "10d"
            71, 73, 75 -> "13d"
            95 -> "11d"
            else -> "04d"
        }
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}