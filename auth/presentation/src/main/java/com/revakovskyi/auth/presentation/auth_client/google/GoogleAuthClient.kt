package com.revakovskyi.auth.presentation.auth_client.google

import com.revakovskyi.auth.presentation.auth_client.firebase.FirebaseAuthenticator
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.domain.utils.asEmptyDataResult
import com.revakovskyi.core.domain.utils.successfulResult

/**
 * Interface for handling authentication via Google, including sign-in and sign-out flows.
 */
internal interface GoogleAuthClient {
    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?
    suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
    suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
}


/**
 * Default implementation of [GoogleAuthClient] that handles sign-in/sign-out using
 * Firebase Authentication and Google Credential Manager.
 */
internal class GoogleAuthClientImpl(
    private val firebaseAuthenticator: FirebaseAuthenticator,
) : GoogleAuthClient {


    override fun isSignedIn(): Boolean = firebaseAuthenticator.isSignedIn()

    override fun getSignedInUser(): User? = firebaseAuthenticator.getUser()

    override suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError> {
        if (isSignedIn()) return successfulResult()

        return when (val result = manager.getAuthCredential()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                val authCredential = result.data
                firebaseAuthenticator.signInWithGoogleAuthCredential(authCredential)
            }
        }
    }

    override suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError> {
        return when (val result = manager.clear()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                firebaseAuthenticator.signOut()
                successfulResult()
            }
        }
    }

}
