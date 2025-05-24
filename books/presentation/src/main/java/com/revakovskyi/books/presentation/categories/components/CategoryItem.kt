package com.revakovskyi.books.presentation.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.domain.books.Category
import com.revakovskyi.core.presentation.theme.dimens

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    showDivider: Boolean,
    onCategoryClick: (categoryName: String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(category.name) }
    ) {

        Column(modifier = Modifier.padding(MaterialTheme.dimens.spacing.medium)) {

            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.published_date, category.publishedDate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = MaterialTheme.dimens.spacing.extraSmall),
            )

            Text(
                text = stringResource(
                    R.string.updates,
                    getLocalizedUpdateFrequency(category.updatingFrequency)
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = MaterialTheme.dimens.spacing.extraSmall),
            )

        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.spacing.medium)
            )
        }

    }

}

@Composable
private fun getLocalizedUpdateFrequency(updateFrequency: String): String {
    return when (updateFrequency.uppercase()) {
        "WEEKLY" -> stringResource(R.string.weekly)
        "MONTHLY" -> stringResource(R.string.monthly)
        else -> updateFrequency
    }
}
