package com.revakovskyi.nytbooks

import android.app.Application
import com.revakovskyi.auth.presentation.di.authPresentationModule
import com.revakovskyi.books.presentation.di.booksPresentationModule
import com.revakovskyi.core.data.di.coreDataModule
import com.revakovskyi.core.network.di.coreNetworkModule
import com.revakovskyi.nytbooks.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    coreDataModule, coreNetworkModule,
                    authPresentationModule,
                    booksPresentationModule,
                )
            )
        }
    }

}
