package com.aswanth.rentease.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aswanth.rentease.data.model.RentalSearchState
import com.aswanth.rentease.data.repository.CarRentalRepository
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RentalSearchViewModel @Inject constructor(
	private val placesClient: PlacesClient,
	private val carRentalRepository: CarRentalRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(RentalSearchState())
	val uiState: StateFlow<RentalSearchState> = _uiState.asStateFlow()

	fun updatePickupLocation(query: String) {
		_uiState.update { it.copy(pickupLocationQuery = query) }
		fetchPlaceSuggestions(query) { suggestions ->
			_uiState.update { it.copy(pickupSuggestions = suggestions) }
		}
	}

	fun updateDropOffLocation(query: String) {
		_uiState.update { it.copy(dropOffLocationQuery = query) }
		fetchPlaceSuggestions(query) { suggestions ->
			_uiState.update { it.copy(dropOffSuggestions = suggestions) }
		}
	}

	fun selectPickupLocation(location: String) {
		_uiState.update { it.copy(pickupLocationQuery = location, pickupSuggestions = emptyList()) }
	}

	fun selectDropOffLocation(location: String) {
		_uiState.update { it.copy(dropOffLocationQuery = location, dropOffSuggestions = emptyList()) }
	}

	fun updatePickupDate(date: String) {
		_uiState.update { it.copy(pickupDate = date) }
	}

	fun updateDropOffDate(date: String) {
		_uiState.update { it.copy(dropOffDate = date) }
	}

	fun searchRentals(): String {
		return carRentalRepository.generateKayakUrl(
			_uiState.value.pickupLocationQuery,
			_uiState.value.dropOffLocationQuery,
			_uiState.value.pickupDate,
			_uiState.value.dropOffDate
		)
	}

	private fun fetchPlaceSuggestions(query: String, onResult: (List<String>) -> Unit) {
		val request = com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest.builder()
			.setQuery(query)
			.build()

		placesClient.findAutocompletePredictions(request)
			.addOnSuccessListener { response ->
				val suggestions = response.autocompletePredictions.map { it.getFullText(null).toString() }
				onResult(suggestions)
			}
			.addOnFailureListener { exception ->
				Log.e("PlacesAPI", "Error fetching place predictions: ${exception.message}")
				onResult(emptyList())
			}
	}
}
