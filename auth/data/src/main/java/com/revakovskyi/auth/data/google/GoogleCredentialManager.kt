package com.revakovskyi.auth.data.google

import android.content.Context
import android.util.Log
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
import com.revakovskyi.auth.data.R
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.util.EmptyDataResult
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.domain.util.successfulResult
import kotlinx.coroutines.CancellationException

interface GoogleCredentialManager {
    suspend fun getAuthCredential(): Result<AuthCredential, AuthError>
    suspend fun clear(): EmptyDataResult<AuthError>
}


internal class GoogleCredentialManagerImpl(
    private val context: Context,
) : GoogleCredentialManager {

    private val tag = "GoogleCredentialManagerImpl"

    private val credentialManager = CredentialManager.create(context)


    override suspend fun getAuthCredential(): Result<AuthCredential, AuthError> {
        val request: GetCredentialRequest = buildCredentialRequest()

        // TODO: delete it later!
        Log.d("TAG_Max", "getCredential")
        Log.d("TAG_Max", "request = $request")
        Log.d("TAG_Max", "")

        return try {
            val credential = credentialManager
                .getCredential(context, request)
                .credential

            if (credential.valid()) {
                val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                // TODO: delete it later!
                Log.d("TAG_Max", "getCredential")
                Log.d("TAG_Max", "googleIdToken = $googleIdToken")
                Log.d("TAG_Max", "credential = $authCredential")
                Log.d("TAG_Max", "")

                Result.Success(authCredential)
            } else {
                Log.e(tag, "Unsupported credential type")
                Result.Error(AuthError.Google.UNSUPPORTED_CREDENTIAL_TYPE)
            }
        } catch (e: Exception) {
            Log.e(tag, "Credential fetch failed", e)
            if (e is CancellationException) throw e
            Result.Error(AuthError.Google.CREDENTIAL_FETCH_FAILED)
        }
    }

    override suspend fun clear(): EmptyDataResult<AuthError> {
        return try {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            successfulResult()
        } catch (e: Exception) {
            Log.e(tag, "Couldn't clear credentials", e)
            Result.Error(AuthError.Google.CREDENTIAL_CLEAR_FAILED)
        }
    }

    private fun buildCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
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
