package com.revakovskyi.books.presentation.books_by_category.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

@Composable
fun StylableText(
    modifier: Modifier = Modifier,
    text: String,
) {

    Text(
        modifier = modifier,
        text = makeFirstWordBold(text = text),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground,
    )

}


@Composable
private fun makeFirstWordBold(text: String): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        val endIndex = text.takeIf { it.contains(":") }?.indexOf(":") ?: 0
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = 0,
            end = endIndex
        )
    }
}
