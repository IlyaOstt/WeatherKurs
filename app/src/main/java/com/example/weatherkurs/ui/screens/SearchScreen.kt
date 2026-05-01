package com.example.weatherkurs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.weatherkurs.R
import com.example.weatherkurs.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    var queryText by remember { mutableStateOf("") }
    val searchResults = viewModel.searchResult.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.screen_bg))
    ) {
        TextField(
            value = queryText,
            onValueChange = {
                queryText = it
                viewModel.onQueryChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_std))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.inner_corner))),
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.card_bg),
                unfocusedContainerColor = colorResource(R.color.card_bg),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_std)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_tiny))
        ) {
            items(searchResults) { city ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.saveCity(city)
                            navController.popBackStack()
                        },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.inner_corner)),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_bg))
                ) {
                    ListItem(
                        headlineContent = { Text(city.name, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text(city.country ?: "") },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }
        }
    }
}