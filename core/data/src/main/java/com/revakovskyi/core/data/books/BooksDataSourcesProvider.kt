package com.revakovskyi.core.data.books

import com.revakovskyi.core.database.BooksDbClient
import com.revakovskyi.core.network.BooksNetworkClient

interface BooksDataSourcesProvider {
    val dbClient: BooksDbClient
    val networkClient: BooksNetworkClient
}


internal class BooksDataSourcesProviderImpl(
    private val booksDbClient: BooksDbClient,
    private val booksNetworkClient: BooksNetworkClient,
) : BooksDataSourcesProvider {

    override val dbClient: BooksDbClient
        get() = booksDbClient

    override val networkClient: BooksNetworkClient
        get() = booksNetworkClient

}
