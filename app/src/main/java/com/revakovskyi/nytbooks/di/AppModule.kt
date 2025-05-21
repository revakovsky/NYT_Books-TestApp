package com.revakovskyi.nytbooks.di

import com.revakovskyi.core.domain.util.DispatcherProvider
import com.revakovskyi.core.domain.util.StandardDispatchers
import com.revakovskyi.nytbooks.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)

    singleOf(::StandardDispatchers).bind<DispatcherProvider>()

}
