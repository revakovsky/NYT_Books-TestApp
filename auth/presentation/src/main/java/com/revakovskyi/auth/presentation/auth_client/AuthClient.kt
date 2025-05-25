package com.revakovskyi.auth.presentation.auth_client

import com.revakovskyi.auth.presentation.auth_client.google.GoogleAuthClient
import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManager
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.utils.EmptyDataResult

/**
 * Interface for managing high-level authentication operations within the app.
 *
 * Abstracts the authentication mechanism (e.g., Google, Email/Password) to allow
 * interchangeable implementations.
 */
interface AuthClient {
    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?
    suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
    suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
}


/**
 * Default implementation of [AuthClient] that delegates to [GoogleAuthClient].
 */
internal class AppAuthClient(
    private val googleAuthClient: GoogleAuthClient,
) : AuthClient {

    override fun isSignedIn(): Boolean {
        return googleAuthClient.isSignedIn()
    }

    override fun getSignedInUser(): User? {
        return googleAuthClient.getSignedInUser()
    }

    override suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError> {
        return googleAuthClient.signIn(manager)
    }

    override suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError> {
        return googleAuthClient.signOut(manager)
    }

}
