package com.revakovskyi.auth.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revakovskyi.auth.presentation.R
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.theme.dimens

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    primaryText: String = stringResource(R.string.sign_in_with_google),
    secondaryText: String = stringResource(R.string.please_wait),
    @DrawableRes iconResId: Int = R.drawable.google_logo,
    shape: Shape = RoundedCornerShape(100),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
) {
    var buttonText by remember { mutableStateOf(primaryText) }


    LaunchedEffect(loading) {
        buttonText = if (loading) secondaryText else primaryText
    }

    Surface(
        color = backgroundColor.copy(alpha = 0.3f),
        modifier = modifier
            .clip(shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(enabled = !loading) { onClick() },
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.spacing.medium)
                .animateContentSize(),
        ) {

            Crossfade(
                label = "",
                targetState = loading,
            ) { isLoading ->
                when (isLoading) {
                    true -> {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            color = progressIndicatorColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    false -> {
                        Icon(
                            painter = painterResource(iconResId),
                            contentDescription = stringResource(R.string.google_logo),
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = buttonText,
                color = MaterialTheme.colorScheme.onBackground,
                style = if (loading) MaterialTheme.typography.bodySmall
                else MaterialTheme.typography.bodyMedium,
            )

        }

    }

}


@Preview
@Composable
private fun PreviewGoogleButton() {
    NYTBooksTheme {
        GoogleButton(
            onClick = {}
        )
    }
}
