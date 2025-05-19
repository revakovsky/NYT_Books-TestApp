package com.revakovskyi.core.presentation.utils.snack_bar

import androidx.compose.material3.SnackbarDuration

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null,
    val snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
)
