package com.revakovskyi.nytbooks

import android.app.Application
import com.revakovskyi.auth.presentation.di.authPresentationModule
import com.revakovskyi.books.data.di.booksDataModule
import com.revakovskyi.books.presentation.di.booksPresentationModule
import com.revakovskyi.core.data.di.coreDataModule
import com.revakovskyi.core.database.di.coreDatabaseModule
import com.revakovskyi.core.network.di.coreNetworkModule
import com.revakovskyi.nytbooks.di.appModule
import com.revakovskyi.nytbooks.utils.DefaultLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        DefaultLogger.init(isDebug = BuildConfig.DEBUG, defaultTag = "NYT_Books")
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    coreDataModule, coreNetworkModule, coreDatabaseModule,
                    authPresentationModule,
                    booksDataModule, booksPresentationModule,
                )
            )
        }
    }

}
