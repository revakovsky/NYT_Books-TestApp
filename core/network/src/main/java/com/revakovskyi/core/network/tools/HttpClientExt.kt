package com.revakovskyi.core.network.tools

import android.util.Log
import com.revakovskyi.core.domain.util.DataError
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

/**
 * Executes a network call safely, converting exceptions into [Result] errors.
 *
 * - Handles common exceptions like network unavailability and serialization issues.
 * - Re-throws [CancellationException] to preserve coroutine behavior.
 *
 * @param execute A lambda that executes the network call and returns an [HttpResponse].
 * @return A [Result] containing either the parsed response body or a network error.
 */
suspend inline fun <reified T> safeNetworkCall(
    dispatcher: CoroutineDispatcher,
    noinline execute: suspend () -> HttpResponse,
): Result<T, DataError.Network> {
    return withContext(dispatcher) {
        try {
            responseToResult(execute())
        } catch (e: UnresolvedAddressException) {
            Log.e("SafeNetworkCall", "No internet", e)
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Log.e("SafeNetworkCall", "Serialization error", e)
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Log.e("SafeNetworkCall", "Unknown network error", e)
            if (e is CancellationException) throw e
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}


/**
 * Converts an HTTP response to a [Result] object.
 *
 * - Maps HTTP status codes to predefined [DataError.Network] error types.
 * - Extracts the response body for success cases.
 *
 * @param response The [HttpResponse] to process.
 * @return A [Result] containing either the parsed response body or a network error.
 */
suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        403 -> Result.Error(DataError.Network.FORBIDDEN)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}


/**
 * Executes an HTTP GET request and safely handles the result.
 *
 * - Constructs the URL using [constructRoute].
 * - Adds query parameters to the request.
 *
 * @param route The relative or full URL of the endpoint.
 * @param queryParameters A map of query parameters to include in the request.
 * @return A [Result] containing either the parsed response body or a network error.
 */
suspend inline fun <reified Response : Any> HttpClient.get(
    dispatcher: CoroutineDispatcher,
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): Result<Response, DataError.Network> {
    return safeNetworkCall(dispatcher) {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}


/**
 * Constructs a full URL based on the provided route.
 *
 * - If the route already contains the base URL, it is returned as it is.
 * - If the route starts with "/", it is appended to the base URL.
 * - Otherwise, the route is appended to the base URL with a "/" separator.
 *
 * @param route The relative or full URL.
 * @return The constructed full URL as a [String].
 */
fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/$route"
    }
}
