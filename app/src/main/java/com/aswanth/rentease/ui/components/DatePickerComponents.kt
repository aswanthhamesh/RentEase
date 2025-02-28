package com.aswanth.rentease.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerField(label: String, date: String, onDateSelected: (String) -> Unit) {
	val context = LocalContext.current
	var selectedDate by remember { mutableStateOf(date) }
	var showDialog by remember { mutableStateOf(false) }

	if (showDialog) {
		DatePickerDialog(
			context,
			{ _, year, month, dayOfMonth ->
				val formattedDate = LocalDate.of(year, month + 1, dayOfMonth)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				selectedDate = formattedDate
				onDateSelected(formattedDate)
				showDialog = false
			},
			LocalDate.now().year,
			LocalDate.now().monthValue - 1,
			LocalDate.now().dayOfMonth
		).apply {
			setOnDismissListener { showDialog = false }
			show()
		}
	}

	// Ensuring the full row (including icon) is clickable
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp)
			.clickable { showDialog = true }  // Click anywhere to open DatePicker
	) {
		OutlinedTextField(
			value = if (selectedDate.isNotEmpty()) formatDisplayDate(selectedDate) else "",
			onValueChange = {},
			label = { Text(label) },
			readOnly = true,
			trailingIcon = {
				Icon(
					imageVector = Icons.Filled.CalendarToday,
					contentDescription = "Select Date",
					modifier = Modifier
						.size(24.dp)
						.clickable { showDialog = true }
				)
			},
			modifier = Modifier.fillMaxWidth()
		)
	}
}

// Format Date for Display
fun formatDisplayDate(dateString: String): String {
	return try {
		val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
	} catch (e: Exception) {
		""
	}
}
