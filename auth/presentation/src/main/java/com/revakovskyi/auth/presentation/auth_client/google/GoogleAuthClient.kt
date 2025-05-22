package com.revakovskyi.auth.presentation.auth_client.google

import com.revakovskyi.auth.presentation.auth_client.firebase.FirebaseAuthenticator
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.util.EmptyDataResult
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.domain.util.asEmptyDataResult
import com.revakovskyi.core.domain.util.successfulResult

interface GoogleAuthClient {
    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?
    suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
    suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
}


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
