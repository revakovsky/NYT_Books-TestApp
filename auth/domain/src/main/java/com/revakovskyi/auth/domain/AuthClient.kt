package com.revakovskyi.auth.domain

import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.util.EmptyDataResult

interface AuthClient {

    fun isSignedIn(): Boolean
    fun getSignedInUser(): User?

    suspend fun signIn(): EmptyDataResult<AuthError>
    suspend fun signOut(): EmptyDataResult<AuthError>

}
