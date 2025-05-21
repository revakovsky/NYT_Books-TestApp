package com.revakovskyi.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.revakovskyi.core.presentation.theme.NYTBooksTheme

@Composable
fun SignInScreenRoot(
    viewModel: SignInViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SignInScreen(
        state = state,
        onAction = viewModel::onAction
    )

}


@Composable
private fun SignInScreen(
    state: SignInState,
    onAction: (action: SignInAction) -> Unit,
) {

}


@PreviewScreenSizes
@Composable
private fun Preview() {
    NYTBooksTheme {
        SignInScreen(
            state = SignInState(),
            onAction = {}
        )
    }
}
