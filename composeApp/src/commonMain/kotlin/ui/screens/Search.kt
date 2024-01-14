package ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import model.SearchableItem
import presentation.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : SearchableItem> Search(
    placeholder: String,
    viewModel: HomeScreenViewModel,
    itemList: List<T>,
    content: @Composable ((T) -> Unit)
) {
    val searchText by viewModel.searchText.collectAsState()
    val isActive by viewModel.isActive.collectAsState()

    DockedSearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = viewModel::onSearchTextChange,
        onSearch = { viewModel.onActiveChange(false) },
        active = isActive,
        onActiveChange = viewModel::onActiveChange,
        placeholder = { Text("Search $placeholder") },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (isActive) {
                IconButton(onClick = {
                    if (searchText.isNotBlank()) viewModel.onSearchTextChange("") else
                        viewModel.onActiveChange(false)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        LazyColumn {
            items(itemList, key = {it.keyId}){
                content(it)
            }
        }
    }
}