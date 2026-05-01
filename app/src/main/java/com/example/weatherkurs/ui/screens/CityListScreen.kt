package com.example.weatherkurs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherkurs.R
import com.example.weatherkurs.utils.WeatherMapper
import com.example.weatherkurs.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(viewModel: WeatherViewModel, navController: NavController) {
    LaunchedEffect(Unit) { viewModel.loadMainList() }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.main_title),
                    fontWeight = FontWeight.Bold
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("search") },
                containerColor = colorResource(R.color.accent_blue),
                contentColor = Color.White
            ) { Icon(Icons.Default.Add, null) }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.screen_bg)),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_std)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(viewModel.citiesWeather.value) { city ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("detail/${city.lat.toFloat()}/${city.lon.toFloat()}/${city.name}")
                        },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner)),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_bg)),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_std))
                ) {
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_std)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = city.name,
                                fontSize = dimensionResource(R.dimen.font_h2).value.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = city.temp,
                                color = colorResource(R.color.accent_blue),
                                fontWeight = FontWeight.Black,
                                fontSize = dimensionResource(R.dimen.font_h2).value.sp
                            )
                        }
                        AsyncImage(
                            model = WeatherMapper.getIconUrl(city.iconCode),
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_main))
                        )
                        IconButton(onClick = { viewModel.removeCity(city.name) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = colorResource(R.color.error_red)
                            )
                        }
                    }
                }
            }
        }
    }
}