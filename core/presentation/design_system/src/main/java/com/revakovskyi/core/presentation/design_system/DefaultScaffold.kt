package com.revakovskyi.core.presentation.design_system

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    content: @Composable (padding: PaddingValues) -> Unit = {},
) {

    Scaffold(
        topBar = topAppBar,
        modifier = modifier,
    ) { paddingValues ->

        content(paddingValues)

    }

}
