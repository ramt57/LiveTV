package ui.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(){
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = { searchText = it },
        onSearch = {
            isActive = false
            searchText = ""
        },
        active = isActive,
        onActiveChange = {
            isActive = it
        },
        placeholder = {
            Text("Search ${LocalTabNavigator.current.current.options.title}")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (isActive) {
                IconButton(onClick = {
                    if (searchText.isNotBlank()) searchText = "" else
                        isActive = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        repeat(4) { idx ->
            val resultText = "Suggestion $idx"
            ListItem(
                headlineContent = { Text(resultText) },
                supportingContent = { Text("Additional info") },
                leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                modifier = Modifier
                    .clickable {
                        searchText = resultText
                        isActive = false
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}