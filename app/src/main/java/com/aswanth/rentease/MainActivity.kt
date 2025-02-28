package com.aswanth.rentease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aswanth.rentease.ui.screens.RentalSearchScreen
import com.aswanth.rentease.ui.theme.RentEaseTheme
import com.aswanth.rentease.viewmodel.RentalSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RentEaseTheme {
				val viewmodel = viewModel(modelClass = RentalSearchViewModel::class)
				RentalSearchScreen(viewmodel)
			}
		}
	}
}