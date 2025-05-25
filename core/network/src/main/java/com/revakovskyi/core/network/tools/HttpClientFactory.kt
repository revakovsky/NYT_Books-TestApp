package com.revakovskyi.core.network.tools

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * Builder class for creating a customized [HttpClient] instance using Ktor.
 *
 * This builder provides a flexible way to configure various aspects of the HTTP client:
 *
 * - JSON content negotiation using Kotlinx Serialization
 * - Optional full HTTP logging via Timber
 * - Configurable timeout settings to control network responsiveness
 * - Default `Content-Type` for all requests (e.g., application/json)
 * - Strict response validation via `expectSuccess` flag
 *
 * Example usage:
 * ```
 * val client = HttpClientBuilder(CIO.create())
 *     .json(Json { ignoreUnknownKeys = true })
 *     .enableLogging(true)
 *     .expectSuccess(true)
 *     .timeouts(request = 20_000, connect = 10_000, socket = 20_000)
 *     .contentType(ContentType.Application.Json)
 *     .build()
 * ```
 *
 * @param engine The HTTP engine to be used (e.g., CIO, OkHttp).
 */
internal class HttpClientBuilder(private val engine: HttpClientEngine) {

    private var json: Json = Json { ignoreUnknownKeys = true }
    private var enableLogging: Boolean = true
    private var expectSuccess: Boolean = false
    private var timeouts: Triple<Long, Long, Long> = Triple(15_000, 10_000, 15_000)
    private var contentType: ContentType = ContentType.Application.Json


    fun json(json: Json) = apply { this.json = json }

    fun enableLogging(enable: Boolean) = apply { this.enableLogging = enable }

    fun expectSuccess(expect: Boolean) = apply { this.expectSuccess = expect }

    fun timeouts(request: Long = 15_000, connect: Long = 10_000, socket: Long = 15_000) = apply {
        this.timeouts = Triple(request, connect, socket)
    }

    fun contentType(contentType: ContentType) = apply { this.contentType = contentType }

    fun build(): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(this@HttpClientBuilder.json)
            }

            if (enableLogging) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) = Timber.d(message)
                    }
                    level = LogLevel.ALL
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = timeouts.first
                connectTimeoutMillis = timeouts.second
                socketTimeoutMillis = timeouts.third
            }

            defaultRequest {
                contentType(contentType)
            }

            this.expectSuccess = this@HttpClientBuilder.expectSuccess
        }
    }

}
