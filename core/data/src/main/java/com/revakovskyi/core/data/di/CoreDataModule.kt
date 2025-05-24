package com.revakovskyi.core.data.di

import com.revakovskyi.core.data.books.BooksDataSourcesProvider
import com.revakovskyi.core.data.books.BooksDataSourcesProviderImpl
import com.revakovskyi.core.data.connectivity.InternetConnectivityObserver
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {

    singleOf(::InternetConnectivityObserver).bind<ConnectivityObserver>()
    singleOf(::BooksDataSourcesProviderImpl).bind<BooksDataSourcesProvider>()

}
