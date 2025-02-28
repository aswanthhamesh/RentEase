package com.aswanth.rentease.di

import android.app.Application
import com.aswanth.rentease.BuildConfig
import com.aswanth.rentease.data.repository.CarRentalRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideCarRentalRepository(): CarRentalRepository {
		return CarRentalRepository()
	}

	@Provides
	@Singleton
	fun providePlacesClient(application: Application): PlacesClient {
		// Initialize Places SDK with your API key
		Places.initialize(
			application,
			BuildConfig.GOOGLE_MAPS_API_KEY
		) // Access the API key from BuildConfig
		return Places.createClient(application)
	}
}