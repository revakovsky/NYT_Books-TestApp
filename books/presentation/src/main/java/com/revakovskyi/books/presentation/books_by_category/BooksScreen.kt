package com.revakovskyi.books.presentation.books_by_category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovskyi.books.presentation.R
import com.revakovskyi.books.presentation.books_by_category.components.BookItem
import com.revakovskyi.books.presentation.books_by_category.components.StoresDialog
import com.revakovskyi.core.presentation.design_system.DefaultPullRefreshBox
import com.revakovskyi.core.presentation.design_system.DefaultScaffold
import com.revakovskyi.core.presentation.design_system.DefaultToolbar
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarController
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun BooksScreenRoot(
    viewModel: BooksViewModel = koinViewModel(),
    backToCategories: () -> Unit,
    openStore: (url: String) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()


    ObserveSingleEvent(viewModel.event) { event ->
        when (event) {
            BooksEvent.BackToCategories -> backToCategories()
            is BooksEvent.OpenStore -> openStore(event.url)

            BooksEvent.SuccessfullyRefreshed -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = context.getString(R.string.successfully_refreshed))
                )
            }

            is BooksEvent.DataError -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = event.message.asString(context))
                )
            }
        }
    }

    BooksScreen(
        state = state,
        onAction = viewModel::onAction
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BooksScreen(
    state: BooksState,
    onAction: (action: BooksAction) -> Unit,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)


    DefaultScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topAppBar = {
            DefaultToolbar(
                showBackButton = true,
                titleSingleLine = true,
                scrollBehavior = scrollBehavior,
                title = state.categoryName,
                titleStyle = MaterialTheme.typography.bodyLarge.copy(),
                onBackClick = { onAction(BooksAction.BackToCategories) },
            )
        },
    ) { paddingValues ->

        DefaultPullRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isRefreshing = state.isRefreshing,
            onRefresh = { onAction(BooksAction.ForceRefreshBooks) },
            content = {

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = state.books,
                        key = { _, it -> it.id }
                    ) { index, book ->

                        BookItem(
                            book = book,
                            showDivider = index != state.books.lastIndex,
                            onBuyClick = { bookId -> onAction(BooksAction.BuyBook(bookId)) }
                        )

                    }
                }

            },
        )

    }


    AnimatedVisibility(
        label = "",
        visible = state.showStoresDialog
    ) {
        StoresDialog(
            stores = state.stores,
            onDismiss = { onAction(BooksAction.HideStoresDialog) },
            onStoreSelected = { storeUrl -> onAction(BooksAction.StoreSelected(storeUrl)) }
        )
    }

}


@Preview()
@Composable
private fun Preview() {
    NYTBooksTheme {
        BooksScreen(
            state = BooksState(),
            onAction = {}
        )
    }
}
