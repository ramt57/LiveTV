package ui.screens.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import model.CategoryItem
import presentation.HomeScreenViewModel

class TabCategory(private val navigator: Navigator) : Tab {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val screenModel = navigator.getNavigatorScreenModel<HomeScreenViewModel>()
        val filterCategories by screenModel.filteredCategory.collectAsState()
        LaunchedEffect(screenModel) {
            screenModel.updateCategories(false)
        }
        LazyColumn {
            items(filterCategories, key = { it.keyId }) {
                CategoryRow(
                    modifier = Modifier.clickable {
                        screenModel.onSearchTextChange(it.id)
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
            val icon = rememberVectorPainter(Icons.Default.List)
            val title = "Category"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}

@Composable
fun CategoryRow(modifier: Modifier, it: CategoryItem, colors: ListItemColors? = null) {
    ListItem(
        headlineContent = { Text(it.name) },
        leadingContent = { Icon(imageVector = Icons.Default.List, contentDescription = "") },
        modifier = modifier,
        colors = colors ?: ListItemDefaults.colors()
    )
}