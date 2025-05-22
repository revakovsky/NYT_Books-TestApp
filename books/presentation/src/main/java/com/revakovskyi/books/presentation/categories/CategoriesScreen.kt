package com.revakovskyi.books.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.revakovskyi.books.presentation.R
import com.revakovskyi.core.presentation.design_system.DefaultScaffold
import com.revakovskyi.core.presentation.design_system.DefaultToolbar
import com.revakovskyi.core.presentation.design_system.util.DropDownItem
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.theme.dimens
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent

@Composable
fun CategoriesScreenRoot(
    viewModel: CategoriesViewModel = viewModel(),
    signOut: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    ObserveSingleEvent(viewModel.event) { event ->
        when (event) {
            CategoriesEvent.SignOut -> signOut()
        }
    }

    CategoriesScreen(
        state = state,
        onAction = viewModel::onAction
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesScreen(
    state: CategoriesState,
    onAction: (action: CategoriesAction) -> Unit,
) {
    val context = LocalContext.current
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)

    val signOutIcon = ImageVector.vectorResource(R.drawable.sign_out)

    val menuItems = remember {
        listOf(
            DropDownItem(
                icon = signOutIcon,
                title = context.getString(R.string.sign_out)
            ),
        )
    }

    DefaultScaffold(
        topAppBar = {
            DefaultToolbar(
                showBackButton = false,
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.round_logo),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                },
                menuItems = menuItems,
                onMenuItemClick = { itemIndex ->
                    when (itemIndex) {
                        0 -> onAction(CategoriesAction.SignOut)
                        else -> Unit
                    }
                }
            )
        },
    ) { paddingValues ->

        val numbers = remember { (1..100).toList() }

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(numbers) { number ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.dimens.spacing.medium,
                            vertical = MaterialTheme.dimens.spacing.small,
                        )
                ) {
                    Text(
                        text = number.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

    }

}


@PreviewScreenSizes
@Composable
private fun Preview() {
    NYTBooksTheme {
        CategoriesScreen(
            state = CategoriesState(),
            onAction = {}
        )
    }
}
