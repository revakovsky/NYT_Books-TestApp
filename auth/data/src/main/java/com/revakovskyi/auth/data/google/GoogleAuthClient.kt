package com.revakovskyi.auth.data.google

import com.revakovskyi.auth.data.firebase.FirebaseAuthenticator
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.util.EmptyDataResult
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.domain.util.asEmptyDataResult
import com.revakovskyi.core.domain.util.successfulResult

interface GoogleAuthClient {
    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?
    suspend fun signIn(): EmptyDataResult<AuthError>
    suspend fun signOut(): EmptyDataResult<AuthError>
}


internal class GoogleAuthClientImpl(
    private val credentialManager: GoogleCredentialManager,
    private val firebaseAuthenticator: FirebaseAuthenticator,
) : GoogleAuthClient {


    override fun isSignedIn(): Boolean = firebaseAuthenticator.isSignedIn()

    override fun getSignedInUser(): User? = firebaseAuthenticator.getUser()

    override suspend fun signIn(): EmptyDataResult<AuthError> {
        if (isSignedIn()) return successfulResult()

        return when (val result = credentialManager.getAuthCredential()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                val authCredential = result.data
                firebaseAuthenticator.signInWithGoogleAuthCredential(authCredential)
            }
        }
    }

    override suspend fun signOut(): EmptyDataResult<AuthError> {
        return when (val result = credentialManager.clear()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                firebaseAuthenticator.signOut()
                successfulResult()
            }
        }
    }

}
