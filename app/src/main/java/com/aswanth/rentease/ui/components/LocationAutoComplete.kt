package com.aswanth.rentease.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LocationAutoComplete(
	label: String,
	query: String,
	onQueryChange: (String) -> Unit,
	suggestions: List<String>,
	onSuggestionSelect: (String) -> Unit
) {
	var inputText by remember { mutableStateOf(TextFieldValue(query)) }
	val coroutineScope = rememberCoroutineScope()

	Column {
		TextField(
			value = inputText,
			onValueChange = {
				inputText = it
				coroutineScope.launch {
					delay(500) // Debounce effect
					onQueryChange(it.text)
				}
			},
			label = { Text(label) },
			modifier = Modifier.fillMaxWidth()
		)

		suggestions.forEach { suggestion ->
			TextButton(onClick = { onSuggestionSelect(suggestion) }) {
				Text(suggestion)
			}
		}
	}
}

