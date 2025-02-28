package com.aswanth.rentease.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aswanth.rentease.ui.components.DatePickerField
import com.aswanth.rentease.ui.components.LocationAutoComplete
import com.aswanth.rentease.viewmodel.RentalSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalSearchScreen(viewModel: RentalSearchViewModel) {
	val uiState by viewModel.uiState.collectAsState()
	val context = LocalContext.current

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("RentEase - Car Rentals") }
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			LocationAutoComplete(
				label = "Pickup Location",
				query = uiState.pickupLocationQuery,
				onQueryChange = { viewModel.updatePickupLocation(it) },
				suggestions = uiState.pickupSuggestions,
				onSuggestionSelect = { viewModel.selectPickupLocation(it) }
			)

			LocationAutoComplete(
				label = "Drop-off Location",
				query = uiState.dropOffLocationQuery,
				onQueryChange = { viewModel.updateDropOffLocation(it) },
				suggestions = uiState.dropOffSuggestions,
				onSuggestionSelect = { viewModel.selectDropOffLocation(it) }
			)

			DatePickerField("Pickup Date", uiState.pickupDate) { viewModel.updatePickupDate(it) }
			DatePickerField("Drop-off Date", uiState.dropOffDate) { viewModel.updateDropOffDate(it) }

			Button(
				onClick = {
					val kayakUrl = viewModel.searchRentals()
					val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kayakUrl))
					context.startActivity(intent)
				},
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Search Rentals")
			}
		}
	}
}
