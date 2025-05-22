package com.revakovskyi.auth.presentation

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManagerImpl
import com.revakovskyi.auth.presentation.components.GoogleButton
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.theme.dimens
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarController
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreenRoot(
    viewModel: SignInViewModel = koinViewModel(),
    onSuccessfulSignIn: () -> Unit,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val state by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        if (!state.isCredentialManagerInitialized) {
            activity?.let {
                viewModel.onAction(
                    SignInAction.InitGoogleCredentialManager(GoogleCredentialManagerImpl(it))
                )
            }
        }
    }

    ObserveSingleEvent(viewModel.event) { event ->
        when (event) {
            SignInEvent.SignInSuccess -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = context.getString(R.string.authenticated_successfully))
                )
                onSuccessfulSignIn()
            }

            SignInEvent.SignOutSuccess -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = context.getString(R.string.signed_out_successfully))
                )
            }

            SignInEvent.RequestCredentialManager -> {
                activity?.let {
                    viewModel.onAction(
                        SignInAction.ForceSignOut(GoogleCredentialManagerImpl(it))
                    )
                }
            }

            is SignInEvent.AuthError -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = event.message.asString(context))
                )
            }
        }
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.spacing.medium)
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.nyt),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.books),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.sign_in_to_continue),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.4f),
            )
        }

        GoogleButton(
            loading = state.isSigningIn,
            onClick = { onAction(SignInAction.SignInWithGoogle) },
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.spacing.medium)
                .padding(bottom = MaterialTheme.dimens.spacing.extraLarge)
        )

    }

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
