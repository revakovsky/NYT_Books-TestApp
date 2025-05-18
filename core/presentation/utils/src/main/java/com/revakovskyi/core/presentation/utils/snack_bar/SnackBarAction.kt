package com.revakovskyi.core.presentation.utils.snack_bar

data class SnackBarAction(
    val name: String,
    val action: suspend () -> Unit,
)
