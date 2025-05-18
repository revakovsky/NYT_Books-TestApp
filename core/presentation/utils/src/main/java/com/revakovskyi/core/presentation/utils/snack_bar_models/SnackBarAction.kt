package com.revakovskyi.core.presentation.utils.snack_bar_models

data class SnackBarAction(
    val name: String,
    val action: suspend () -> Unit,
)
