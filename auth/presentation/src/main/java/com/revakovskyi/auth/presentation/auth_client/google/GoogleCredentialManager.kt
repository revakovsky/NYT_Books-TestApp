package com.revakovskyi.auth.presentation.auth_client.google

import android.app.Activity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.revakovskyi.auth.presentation.R
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.domain.utils.successfulResult
import kotlinx.coroutines.CancellationException
import timber.log.Timber

/**
 * Abstraction for acquiring and clearing Google credentials via the Credential Manager API.
 */
interface GoogleCredentialManager {
    suspend fun getAuthCredential(): Result<AuthCredential, AuthError>
    suspend fun clear(): EmptyDataResult<AuthError>
}


/**
 * Default implementation of [GoogleCredentialManager] using the Credential Manager API.
 *
 * @param activity The activity used to launch the Google sign-in UI.
 */
class GoogleCredentialManagerImpl(
    private val activity: Activity,
) : GoogleCredentialManager {

    private val credentialManager = CredentialManager.create(activity)


    override suspend fun getAuthCredential(): Result<AuthCredential, AuthError> {
        val request: GetCredentialRequest = buildCredentialRequest()

        return try {
            val credential = credentialManager
                .getCredential(activity, request)
                .credential

            if (credential.valid()) {
                val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                Result.Success(authCredential)
            } else {
                Timber.e("Unsupported credential type")
                Result.Error(AuthError.Google.UNSUPPORTED_CREDENTIAL_TYPE)
            }
        } catch (e: Exception) {
            Timber.e(e, "Credential fetch failed")
            if (e is CancellationException) throw e
            Result.Error(AuthError.Google.CREDENTIAL_FETCH_FAILED)
        }
    }

    override suspend fun clear(): EmptyDataResult<AuthError> {
        return try {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            successfulResult()
        } catch (e: Exception) {
            Timber.e(e, "Couldn't clear credentials")
            Result.Error(AuthError.Google.CREDENTIAL_CLEAR_FAILED)
        }
    }

    private fun buildCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(activity.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun Credential.valid(): Boolean =
        this is CustomCredential && this.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL

}
