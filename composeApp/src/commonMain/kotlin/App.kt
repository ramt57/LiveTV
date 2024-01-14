import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.repo.Database
import presentation.repo.DatabaseDriverFactory
import presentation.repo.di.apiModule
import presentation.repo.di.httpClientModule
import presentation.repo.di.viewModelModules
import ui.screens.ScreenHome
import ui.theme.AppTheme

@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    KoinApplication(application = {
        modules(appModule(databaseDriverFactory))
    }) {
        AppTheme {
            Navigator(screen = ScreenHome()) {
                SlideTransition(it)
            }
        }
    }
}

fun appModule(databaseDriverFactory: DatabaseDriverFactory): List<Module> {
    return listOf(
        module {
            single<Database> { Database(databaseDriverFactory) }
        },
        httpClientModule, apiModule, viewModelModules,
    )
}