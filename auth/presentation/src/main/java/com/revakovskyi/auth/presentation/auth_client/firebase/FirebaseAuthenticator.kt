package com.revakovskyi.auth.presentation.auth_client.firebase

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.User
import com.revakovskyi.core.domain.utils.DispatcherProvider
import com.revakovskyi.core.domain.utils.EmptyDataResult
import com.revakovskyi.core.domain.utils.Result
import com.revakovskyi.core.domain.utils.successfulResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface FirebaseAuthenticator {
    fun isSignedIn(): Boolean
    fun getUser(): User?
    suspend fun signInWithGoogleAuthCredential(credential: AuthCredential): EmptyDataResult<AuthError>
    fun signOut()
}


internal class FirebaseAuthenticatorImpl(
    private val dispatcherProvider: DispatcherProvider,
) : FirebaseAuthenticator {

    private val tag = "FirebaseAuthenticatorImpl"

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun isSignedIn(): Boolean = firebaseAuth.currentUser != null

    override fun getUser(): User? = firebaseAuth.currentUser?.run {
        User(
            userId = uid,
            username = displayName,
            email = email,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    override suspend fun signInWithGoogleAuthCredential(credential: AuthCredential): EmptyDataResult<AuthError> {
        return withContext(dispatcherProvider.io) {
            try {
                val result = firebaseAuth.signInWithCredential(credential).await()

                if (result.user != null) successfulResult()
                else Result.Error(AuthError.Firebase.UNKNOWN)

            } catch (e: FirebaseAuthInvalidUserException) {
                Log.e(tag, "Sign-in failed: invalid user", e)
                Result.Error(AuthError.Firebase.INVALID_USER)

            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e(tag, "Sign-in failed: invalid credentials", e)
                Result.Error(AuthError.Firebase.INVALID_CREDENTIALS)

            } catch (e: FirebaseAuthUserCollisionException) {
                Log.e(tag, "Sign-in failed: account collision", e)
                Result.Error(AuthError.Firebase.ACCOUNT_COLLISION)

            } catch (e: Exception) {
                Log.e(tag, "Sign-in failed: unknown error", e)
                if (e is CancellationException) throw e
                Result.Error(AuthError.Firebase.UNKNOWN)
            }
        }
    }

    override fun signOut() = firebaseAuth.signOut()

}
