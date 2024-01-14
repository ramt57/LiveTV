package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.HomeScreenViewModel
import ui.screens.Search

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