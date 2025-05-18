package com.revakovskyi.core.presentation.utils.snack_bar_models

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null,
)
