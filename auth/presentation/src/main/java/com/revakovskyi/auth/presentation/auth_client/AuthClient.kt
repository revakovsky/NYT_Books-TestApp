package com.revakovskyi.auth.presentation.auth_client

import com.revakovskyi.auth.presentation.auth_client.google.GoogleAuthClient
import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManager
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.util.EmptyDataResult

interface AuthClient {
    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?
    suspend fun signIn(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
    suspend fun signOut(manager: GoogleCredentialManager): EmptyDataResult<AuthError>
}


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
