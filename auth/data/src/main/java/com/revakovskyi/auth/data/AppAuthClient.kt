package com.revakovskyi.auth.data

import com.revakovskyi.auth.data.google.GoogleAuthClient
import com.revakovskyi.auth.domain.AuthClient
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.util.EmptyDataResult

internal class AppAuthClient(
    private val googleAuthClient: GoogleAuthClient,
) : AuthClient {

    override fun isSignedIn(): Boolean {
        return googleAuthClient.isSignedIn()
    }

    override fun getSignedInUser(): User? {
        return googleAuthClient.getSignedInUser()
    }

    override suspend fun signIn(): EmptyDataResult<AuthError> {
        return googleAuthClient.signIn()
    }

    override suspend fun signOut(): EmptyDataResult<AuthError> {
        return googleAuthClient.signOut()
    }

}
