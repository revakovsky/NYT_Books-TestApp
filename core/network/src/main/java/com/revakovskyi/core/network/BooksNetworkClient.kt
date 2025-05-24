package com.revakovskyi.core.network

import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.DispatcherProvider
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.network.dto.BooksOverviewDto
import com.revakovskyi.core.network.tools.get
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface BooksNetworkClient {
    fun fetchBooksOverview(): Flow<Result<BooksOverviewDto, DataError.Network>>
}


internal class KtorBooksNetworkClient(
    private val client: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
) : BooksNetworkClient {

    override fun fetchBooksOverview(): Flow<Result<BooksOverviewDto, DataError.Network>> = flow {
        val result = client.get<BooksOverviewDto>(
            dispatcher = dispatcherProvider.io,
            route = BOOKS_OVERVIEW_ROUTE,
            queryParameters = mapOf(API_KEY to BuildConfig.API_KEY)
        )
        emit(result)
    }.flowOn(dispatcherProvider.io)


    private companion object {
        const val BOOKS_OVERVIEW_ROUTE = "lists/overview.json"
        const val API_KEY = "api-key"
    }

}
