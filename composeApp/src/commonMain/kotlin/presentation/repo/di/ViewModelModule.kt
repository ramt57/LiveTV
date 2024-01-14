package presentation.repo.di

import org.koin.dsl.module
import presentation.HomeScreenViewModel

val viewModelModules = module {
    single {
        HomeScreenViewModel(get(), get())
    }
}