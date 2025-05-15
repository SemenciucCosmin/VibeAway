package com.example.vibeaway.ui.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.model.TitleBarMenuItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    label: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    isOverflow: Boolean = false,
    menuItems: ImmutableList<TitleBarMenuItem> = persistentListOf(),
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(label) },
        navigationIcon = {
            onBack?.let {
                IconButton(onClick = it) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = stringResource(R.string.lbl_accessibility_back)
                    )
                }
            }
        },
        actions = {
            when {
                isOverflow -> OverflowMenu(menuItems = menuItems)
                else -> VisibleMenu(menuItems = menuItems)
            }
        }
    )
}

@Composable
private fun OverflowMenu(
    modifier: Modifier = Modifier,
    menuItems: ImmutableList<TitleBarMenuItem>
) {
    var isExpanded by remember { mutableStateOf(false) }

    DropdownMenu(
        modifier = modifier,
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false }
    ) {
        menuItems.forEach { item ->
            val label = item.label ?: return@forEach

            DropdownMenuItem(
                text = { Text(text = label) },
                onClick = {
                    isExpanded = false
                    item.action.invoke()
                }
            )
        }
    }
}

@Composable
private fun VisibleMenu(
    modifier: Modifier = Modifier,
    menuItems: ImmutableList<TitleBarMenuItem>
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.XSmall)
    ) {
        menuItems.forEach { item ->
            when {
                item.iconResId != null -> {
                    IconButton(
                        onClick = item.action,
                        enabled = item.enabled
                    ) {
                        Icon(
                            painter = painterResource(item.iconResId),
                            contentDescription = item.contentDescription
                        )
                    }
                }

                item.label != null -> {
                    TextButton(
                        onClick = item.action,
                        enabled = item.enabled
                    ) {
                        Text(text = item.label.uppercase())
                    }
                }
            }
        }
    }
}
