package presentation.repo.di

import org.koin.dsl.module
import presentation.HomeScreenViewModel

val viewModelModules = module {
    factory {
        HomeScreenViewModel(get(), get())
    }
}