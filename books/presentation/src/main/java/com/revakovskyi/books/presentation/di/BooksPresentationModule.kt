package com.revakovskyi.books.presentation.di

import com.revakovskyi.books.presentation.books_by_category.BooksViewModel
import com.revakovskyi.books.presentation.categories.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val booksPresentationModule = module {

    viewModelOf(::CategoriesViewModel)
    viewModelOf(::BooksViewModel)

}
