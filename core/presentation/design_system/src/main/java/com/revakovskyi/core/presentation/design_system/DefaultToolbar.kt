package com.revakovskyi.core.presentation.design_system

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.revakovskyi.core.presentation.design_system.util.DropDownItem
import com.revakovskyi.core.presentation.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultToolbar(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.app_name),
    titleStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 32.sp),
    showBackButton: Boolean,
    @DrawableRes backVectorIconResId: Int = R.drawable.arrow_left,
    menuItems: List<DropDownItem> = emptyList(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onBackClick: () -> Unit = {},
    startContent: (@Composable () -> Unit)? = null,
    onMenuItemClick: (itemIndex: Int) -> Unit = {},
) {
    var isDropDownMenuOpen by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                startContent?.invoke()

                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacing.small))

                Text(
                    text = title,
                    style = titleStyle,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(backVectorIconResId),
                        contentDescription = stringResource(R.string.go_back),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        actions = {
            if (menuItems.isNotEmpty()) {
                Box {

                    DropdownMenu(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                        expanded = isDropDownMenuOpen,
                        onDismissRequest = { isDropDownMenuOpen = false }
                    ) {
                        menuItems.forEachIndexed { itemIndex, dropDownItem ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onMenuItemClick(itemIndex)
                                        isDropDownMenuOpen = false
                                    }
                                    .padding(
                                        horizontal = MaterialTheme.dimens.spacing.medium,
                                        vertical = MaterialTheme.dimens.spacing.small
                                    )
                            ) {
                                Icon(
                                    imageVector = dropDownItem.icon,
                                    contentDescription = dropDownItem.title,
                                    tint = MaterialTheme.colorScheme.background
                                )

                                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacing.medium))

                                Text(
                                    text = dropDownItem.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }

                    IconButton(onClick = { isDropDownMenuOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.open_menu),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                }

            }

        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = modifier,
    )

}
