package com.revakovskyi.books.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.revakovskyi.core.presentation.theme.NYTBooksTheme

@Composable
fun CategoriesScreenRoot(
    viewModel: CategoriesViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        state = state,
        onAction = viewModel::onAction
    )

}


@Composable
private fun CategoriesScreen(
    state: CategoriesState,
    onAction: (action: CategoriesAction) -> Unit,
) {

}


@PreviewScreenSizes
@Composable
private fun Preview() {
    NYTBooksTheme {
        CategoriesScreen(
            state = CategoriesState(),
            onAction = {}
        )
    }
}
