package ui.screens.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import model.CountryItem
import presentation.HomeScreenViewModel

class TabCountry(private val navigator: Navigator) : Tab {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val screenViewModel = navigator.getNavigatorScreenModel<HomeScreenViewModel>()
        val filterCountries by screenViewModel.filteredCountry.collectAsState()
        LaunchedEffect(screenViewModel) {
            screenViewModel.updateCountries(false)
        }
        LazyColumn {
            items(filterCountries, key = { it.keyId }) {
                CountryRow(
                    modifier = Modifier.clickable {
                        screenViewModel.onSearchTextChange(it.code)
                        tabNavigator.current = TabAll(navigator)
                    }.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp), it,
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                )
            }
        }
    }


    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.PlayArrow)
            val title = "Country"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}

@Composable
fun CountryRow(modifier: Modifier, it: CountryItem, colors: ListItemColors? = null) {
    ListItem(
        headlineContent = { Text(it.name) },
        leadingContent = { Text(it.flag) },
        overlineContent = { Text(it.code) },
        modifier = modifier,
        colors = colors ?: ListItemDefaults.colors()
    )
}