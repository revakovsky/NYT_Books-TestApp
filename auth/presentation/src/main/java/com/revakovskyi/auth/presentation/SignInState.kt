package com.revakovskyi.auth.presentation

data class SignInState(
    val isSigningIn: Boolean = false,
    val isCredentialManagerInitialized: Boolean = false,
)
