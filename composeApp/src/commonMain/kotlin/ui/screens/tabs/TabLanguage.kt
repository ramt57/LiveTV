package ui.screens.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import model.CategoryItem
import model.Language
import presentation.HomeScreenViewModel
import ui.theme.AppTheme

class TabLanguage(private val navigator: Navigator) : Tab {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val screenModel = navigator.getNavigatorScreenModel<HomeScreenViewModel>()
        val filterLanguages by screenModel.filteredLanguageList.collectAsState()
        LaunchedEffect(screenModel) {
            screenModel.updateLanguages(false)
        }
        LazyColumn {
            items(filterLanguages, key = { it.keyId }) {
                LanguageRow(
                    modifier = Modifier.clickable {
                        screenModel.onSearchTextChange(it.code)
                        tabNavigator.current = TabAll(navigator)
                    }.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    it,
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                )
            }
        }
    }

    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Info)
            val title = "Language"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}

@Composable
fun LanguageRow(modifier: Modifier, it: Language, colors: ListItemColors? = null) {
    ListItem(
        headlineContent = { Text(it.name) },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = ""
            )
        },
        modifier = modifier,
        colors = colors ?: ListItemDefaults.colors()
    )
}
