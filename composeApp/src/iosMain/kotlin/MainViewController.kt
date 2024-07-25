import androidx.compose.ui.window.ComposeUIViewController
import presentation.repo.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController { App(DatabaseDriverFactory()) }
