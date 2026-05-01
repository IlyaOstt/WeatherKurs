package com.example.weatherkurs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.example.weatherkurs.R
import com.example.weatherkurs.utils.WeatherMapper
import com.example.weatherkurs.viewmodel.WeatherViewModel

@Composable
fun WeatherDetailScreen(lat: Double, lon: Double, name: String, viewModel: WeatherViewModel) {
    LaunchedEffect(Unit) { viewModel.loadDetails(lat, lon) }
    val weatherData = viewModel.detailedWeather.value
    val standardPadding = dimensionResource(R.dimen.padding_std)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.screen_bg))
            .padding(standardPadding)
    ) {
        Text(
            text = name,
            fontSize = dimensionResource(R.dimen.font_h1).value.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.forecast_week),
            color = colorResource(R.color.text_secondary)
        )

        Spacer(modifier = Modifier.height(standardPadding))

        if (weatherData != null) {
            val dailyTimes = weatherData.hourly.time.chunked(24)
            val dailyTemps = weatherData.hourly.temperature_2m.chunked(24)
            val dailyCodes = weatherData.hourly.weather_code.chunked(24)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(standardPadding)) {
                items(dailyTimes.size) { dayIndex ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner)),
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_bg)),
                        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_std))
                    ) {
                        Column(modifier = Modifier.padding(standardPadding)) {
                            Text(
                                text = dailyTimes[dayIndex][0].substringBefore("T"),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
                                items(dailyTimes[dayIndex].size) { hourIndex ->
                                    Surface(
                                        color = colorResource(R.color.hour_card_bg),
                                        shape = RoundedCornerShape(dimensionResource(R.dimen.inner_corner)),
                                        modifier = Modifier.width(dimensionResource(R.dimen.hour_card_width))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = dailyTimes[dayIndex][hourIndex].substringAfter("T"),
                                                fontSize = dimensionResource(R.dimen.font_label).value.sp,
                                                color = Color.White.copy(alpha = 0.7f)
                                            )
                                            AsyncImage(
                                                model = WeatherMapper.getIconUrl(dailyCodes[dayIndex][hourIndex]),
                                                contentDescription = null,
                                                modifier = Modifier.size(dimensionResource(R.dimen.icon_hour))
                                            )
                                            Text(
                                                text = stringResource(R.string.temp_unit, dailyTemps[dayIndex][hourIndex].toString()),
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}