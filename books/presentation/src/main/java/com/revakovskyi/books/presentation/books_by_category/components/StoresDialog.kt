package com.revakovskyi.books.presentation.books_by_category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.domain.books.Store
import com.revakovskyi.core.presentation.theme.dimens

@Composable
fun StoresDialog(
    modifier: Modifier = Modifier,
    stores: List<Store>,
    onDismiss: () -> Unit,
    onStoreSelected: (storeUrl: String) -> Unit,
) {

    Dialog(onDismissRequest = onDismiss) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.dimens.spacing.medium)
        ) {

            Text(
                text = stringResource(R.string.select_a_store),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.spacing.medium)
            ) {
                itemsIndexed(
                    items = stores,
                    key = { _, it -> it.name }
                ) { index, store ->

                    DialogItem(
                        store = store,
                        showDivider = index != stores.lastIndex,
                        onStoreSelected = { onStoreSelected(store.url) }
                    )

                }
            }

        }

    }

}


@Composable
private fun DialogItem(
    store: Store,
    showDivider: Boolean,
    onStoreSelected: () -> Unit,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStoreSelected() }
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = store.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            if (showDivider) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    modifier = Modifier.padding(top = MaterialTheme.dimens.spacing.medium)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

        }

    }

}
