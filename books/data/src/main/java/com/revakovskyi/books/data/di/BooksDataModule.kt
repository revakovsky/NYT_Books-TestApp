package com.revakovskyi.books.data.di

import com.revakovskyi.books.data.BooksRepositoryImpl
import com.revakovskyi.books.domain.BooksRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val booksDataModule = module {

    singleOf(::BooksRepositoryImpl).bind<BooksRepository>()

}
