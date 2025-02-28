package com.aswanth.rentease.data.model

import java.time.LocalDate

data class RentalSearchState(
	val pickupLocationQuery: String = "",
	val dropOffLocationQuery: String = "",
	val pickupSuggestions: List<String> = emptyList(),
	val dropOffSuggestions: List<String> = emptyList(),
	val pickupDate: String = LocalDate.now().toString(),  // Default to today
	val dropOffDate: String = LocalDate.now().plusDays(1).toString()  // Default to tomorrow
)
