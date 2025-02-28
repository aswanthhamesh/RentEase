package com.aswanth.rentease.data.repository

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Singleton

class CarRentalRepository {

	fun generateKayakUrl(
		pickupLocation: String,
		dropOffLocation: String?,
		pickupDate: String,
		dropOffDate: String,
		affiliateId: String = "awesomecars",
		kayakDomain: String = "www.kayak.com"
	): String {
		// Encode locations to avoid errors in the URL
		val encodedPickup = URLEncoder.encode(pickupLocation, StandardCharsets.UTF_8.toString())
		val encodedDropOff = if (!dropOffLocation.isNullOrEmpty())
			URLEncoder.encode(dropOffLocation, StandardCharsets.UTF_8.toString())
		else
			encodedPickup  // If drop-off is empty, use pickup location

		return "https://$kayakDomain/in?a=$affiliateId&url=/cars/$encodedPickup/$encodedDropOff/$pickupDate/$dropOffDate/"
	}
}
