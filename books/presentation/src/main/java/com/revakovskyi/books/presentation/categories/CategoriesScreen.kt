package com.revakovskyi.books.presentation.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovskyi.books.presentation.R
import com.revakovskyi.books.presentation.categories.components.CategoryItem
import com.revakovskyi.core.presentation.design_system.DefaultButton
import com.revakovskyi.core.presentation.design_system.DefaultDialog
import com.revakovskyi.core.presentation.design_system.DefaultOutlinedButton
import com.revakovskyi.core.presentation.design_system.DefaultPullRefreshBox
import com.revakovskyi.core.presentation.design_system.DefaultScaffold
import com.revakovskyi.core.presentation.design_system.DefaultToolbar
import com.revakovskyi.core.presentation.design_system.util.DropDownItem
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarController
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreenRoot(
    viewModel: CategoriesViewModel = koinViewModel(),
    openBooksByCategory: (categoryName: String) -> Unit,
    signOut: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()


    ObserveSingleEvent(viewModel.event) { event ->
        when (event) {
            is CategoriesEvent.OpenBooksByCategory -> openBooksByCategory(event.categoryName)
            CategoriesEvent.SignOut -> signOut()

            is CategoriesEvent.DataError -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = event.message.asString(context))
                )
            }

            CategoriesEvent.SuccessfullyRefreshed -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(message = context.getString(R.string.successfully_refreshed))
                )
            }
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

    var showSignOutDialog by rememberSaveable { mutableStateOf(false) }

    val menuItems = remember {
        listOf(
            DropDownItem(
                icon = signOutIcon,
                title = context.getString(R.string.sign_out_title)
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
                        0 -> showSignOutDialog = true
                        else -> Unit
                    }
                }
            )
        },
    ) { paddingValues ->

        DefaultPullRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isRefreshing = state.isRefreshing,
            onRefresh = { onAction(CategoriesAction.ForceRefreshCategories) },
            content = {

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = state.categories,
                        key = { _, it -> it.name }
                    ) { index, category ->

                        CategoryItem(
                            category = category,
                            showDivider = index != state.categories.lastIndex,
                            onCategoryClick = { categoryName ->
                                onAction(CategoriesAction.OpenBooksByCategory(categoryName))
                            }
                        )

                    }
                }

            },
        )

    }


    AnimatedVisibility(
        label = "",
        visible = showSignOutDialog
    ) {
        DefaultDialog(
            title = stringResource(R.string.sign_out_title),
            description = stringResource(R.string.sign_out_description),
            onDismiss = { showSignOutDialog = false },
            primaryButton = {
                DefaultButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.stay),
                    onClick = { showSignOutDialog = false }
                )
            },
            secondaryButton = {
                DefaultOutlinedButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.sign_out),
                    onClick = {
                        showSignOutDialog = false
                        onAction(CategoriesAction.SignOut)
                    }
                )
            },
        )
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
