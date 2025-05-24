package com.revakovskyi.core.database.di

import androidx.room.Room
import com.revakovskyi.core.database.BooksDbClientImpl
import com.revakovskyi.core.database.BooksDbClient
import com.revakovskyi.core.database.dao.BooksDao
import com.revakovskyi.core.database.db.BooksDb
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabaseModule = module {

    single<BooksDb> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = BooksDb::class.java,
            name = "BooksDb"
        )
            .build()
    }

    single<BooksDao> { get<BooksDb>().booksDao }
    singleOf(::BooksDbClientImpl).bind<BooksDbClient>()

}
