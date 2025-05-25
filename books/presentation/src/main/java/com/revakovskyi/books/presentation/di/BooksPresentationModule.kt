package com.revakovskyi.books.presentation.di

import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.revakovskyi.books.presentation.books_by_category.BooksViewModel
import com.revakovskyi.books.presentation.categories.CategoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val booksPresentationModule = module {

    viewModelOf(::CategoriesViewModel)
    viewModelOf(::BooksViewModel)

    single<ImageLoader> {
        ImageLoader.Builder(androidContext())
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(androidContext())
                    .maxSizePercent(0.25)
                    .build()
            }
            .crossfade(true)
            .build()
    }

}
