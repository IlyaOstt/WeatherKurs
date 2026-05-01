package com.example.weatherkurs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.weatherkurs.ui.screens.CityListScreen
import com.example.weatherkurs.ui.screens.SearchScreen
import com.example.weatherkurs.ui.screens.WeatherDetailScreen
import com.example.weatherkurs.utils.PreferencesManager
import com.example.weatherkurs.viewmodel.SearchViewModel
import com.example.weatherkurs.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferencesManager = PreferencesManager(this)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val weatherViewModel = remember { WeatherViewModel(preferencesManager) }
                val searchViewModel = remember { SearchViewModel(preferencesManager) }

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "list") {
                        composable(route = "list") {
                            CityListScreen(weatherViewModel, navController)
                        }
                        composable(route = "search") {
                            SearchScreen(searchViewModel, navController)
                        }
                        composable(
                            route = "detail/{lat}/{lon}/{name}",
                            arguments = listOf(
                                navArgument("lat") { type = NavType.FloatType },
                                navArgument("lon") { type = NavType.FloatType },
                                navArgument("name") { type = NavType.StringType }
                            )
                        ) { navBackStackEntry ->
                            val latitude = navBackStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
                            val longitude = navBackStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.0
                            val cityName = navBackStackEntry.arguments?.getString("name") ?: ""
                            WeatherDetailScreen(latitude, longitude, cityName, weatherViewModel)
                        }
                    }
                }
            }
        }
    }
}