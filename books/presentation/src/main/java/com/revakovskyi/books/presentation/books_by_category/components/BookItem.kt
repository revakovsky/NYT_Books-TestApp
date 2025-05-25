package com.revakovskyi.books.presentation.books_by_category.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.domain.books.Book
import com.revakovskyi.core.presentation.design_system.DefaultButton
import com.revakovskyi.core.presentation.theme.dimens

@Composable
fun BookItem(
    book: Book,
    showDivider: Boolean,
    onBuyClick: (bookId: String) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.spacing.medium)
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {

            CoilImage(url = book.image)

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier) {

                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(32.dp))

                StylableText(
                    text = stringResource(R.string.book_rank, book.rank),
                )

                Spacer(modifier = Modifier.height(4.dp))

                StylableText(
                    text = stringResource(R.string.book_author, book.author)
                )

                Spacer(modifier = Modifier.height(4.dp))

                StylableText(
                    text = stringResource(R.string.publisher, book.publisher)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DefaultButton(
                    text = stringResource(R.string.buy),
                    onClick = { onBuyClick(book.id) },
                )

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        StylableText(
            text = stringResource(
                R.string.description,
                book.description.ifEmpty {
                    stringResource(R.string.description_is_not_available)
                }
            )
        )

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(top = MaterialTheme.dimens.spacing.medium)
            )
        }

    }

}
