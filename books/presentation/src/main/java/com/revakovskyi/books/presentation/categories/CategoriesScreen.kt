package com.revakovskyi.books.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.presentation.design_system.AppButton
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent

@Composable
fun CategoriesScreenRoot(
    viewModel: CategoriesViewModel = viewModel(),
    signOut: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    ObserveSingleEvent(viewModel.event) { event ->
        when (event) {
            CategoriesEvent.SignOut -> signOut()
        }
    }

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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        AppButton(
            text = stringResource(R.string.sign_out),
            onClick = { onAction(CategoriesAction.SignOut) }
        )

    }

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
