package com.revakovskyi.core.presentation.design_system

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revakovskyi.core.presentation.theme.dimens

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    width: Dp = 100.dp,
    onClick: () -> Unit,
) {

    Button(
        modifier = modifier.width(width),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(100),
        contentPadding = PaddingValues(MaterialTheme.dimens.spacing.small),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background
        )
    }

}
