package com.revakovskyi.auth.presentation

data class SignInState(
    val isLoading: Boolean = true,
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
