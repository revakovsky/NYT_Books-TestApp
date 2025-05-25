package com.revakovskyi.core.network.di

import com.revakovskyi.core.network.BooksNetworkClient
import com.revakovskyi.core.network.KtorBooksNetworkClient
import com.revakovskyi.core.network.tools.HttpClientBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreNetworkModule = module {

    /**
     * Provides a singleton instance of [HttpClient] using the [CIO] engine.
     *
     * This client is pre-configured with:
     * - JSON content negotiation
     * - Full HTTP logging via Timber
     * - Timeout settings to prevent hanging requests
     * - Default request headers (Content-Type: application/json)
     */
    single<HttpClient> { HttpClientBuilder(engine = CIO.create()).build() }

    singleOf(::KtorBooksNetworkClient).bind<BooksNetworkClient>()

}
