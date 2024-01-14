package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.HomeScreenViewModel
import ui.screens.Search
import ui.screens.tabs.CategoryRow
import ui.screens.tabs.CountryRow
import ui.screens.tabs.LanguageRow

@Composable
fun ChannelSearch(viewModel: HomeScreenViewModel, placeholderText: String) {
    val filteredChannels by viewModel.filteredChannels.collectAsState()
    Search(
        placeholderText,
        viewModel,
        filteredChannels
    ) {
        ListItem(
            headlineContent = { Text(it.name) },
            supportingContent = {
                (it.network ?: it.website)?.let { it1 -> Text(it1) }
            },
            leadingContent = {
                AsyncImage(
                    Modifier.width(40.dp).wrapContentHeight(),
                    it.logo ?: ""
                )
            },
            overlineContent = { Text(it.country) },
            modifier = Modifier
                .clickable {
                    viewModel.onSearchTextChange(it.name)
                    viewModel.onActiveChange(false)
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ChannelCountry(viewModel: HomeScreenViewModel, placeholderText: String) {
    val filteredChannels by viewModel.filteredCountry.collectAsState()
    Search(
        placeholderText,
        viewModel,
        filteredChannels
    ) {
        CountryRow(Modifier
            .clickable {
                viewModel.onSearchTextChange(it.name)
                viewModel.onActiveChange(false)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp), it)
    }
}

@Composable
fun ChannelCategory(viewModel: HomeScreenViewModel, placeholderText: String) {
    val filteredChannels by viewModel.filteredCategory.collectAsState()
    Search(
        placeholderText,
        viewModel,
        filteredChannels
    ) {
        CategoryRow(Modifier
            .clickable {
                viewModel.onSearchTextChange(it.name)
                viewModel.onActiveChange(false)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp), it)
    }
}

@Composable
fun ChannelLanguage(viewModel: HomeScreenViewModel, placeholderText: String) {
    val filterLanguages by viewModel.filteredLanguageList.collectAsState()
    Search(
        placeholderText,
        viewModel,
        filterLanguages
    ) {
        LanguageRow(Modifier
            .clickable {
                viewModel.onSearchTextChange(it.name)
                viewModel.onActiveChange(false)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp), it)
    }
}

@Composable
fun ChannelFavourite(viewModel: HomeScreenViewModel, placeholderText: String) {
    val filteredChannels by viewModel.filteredChannels.collectAsState()
    Search(
        placeholderText,
        viewModel,
        filteredChannels
    ) {
        ListItem(
            headlineContent = { Text(it.name) },
            supportingContent = {
                (it.network ?: it.website)?.let { it1 -> Text(it1) }
            },
            leadingContent = {
                AsyncImage(
                    Modifier.width(40.dp).wrapContentHeight(),
                    it.logo ?: ""
                )
            },
            overlineContent = { Text(it.country) },
            modifier = Modifier
                .clickable {
                    viewModel.onSearchTextChange(it.name)
                    viewModel.onActiveChange(false)
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}