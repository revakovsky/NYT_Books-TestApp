package com.revakovskyi.nytbooks.di

import com.revakovskyi.nytbooks.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)

}
