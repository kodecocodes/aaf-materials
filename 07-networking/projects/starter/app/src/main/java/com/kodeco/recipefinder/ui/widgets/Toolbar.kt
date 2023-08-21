package com.kodeco.recipefinder.ui.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CreateToolbar(toolbarInfo: ToolbarInfo?) {
    if (toolbarInfo == null) {
        return
    }
    val navIcon: ComposeFun = if (toolbarInfo.navInfo != null) {
        {
            IconButton(onClick = toolbarInfo.navInfo.navOnClick) {
                Icon(
                    toolbarInfo.navInfo.icon.icon,
                    tint = Color.Black,
                    contentDescription = toolbarInfo.navInfo.icon.contextText
                )
            }
        }
    } else EmptyComposeFun
    TopAppBar(
        title = {
            Text(
                text = toolbarInfo.title, color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors =  TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        actions = toolbarInfo.actions,
        navigationIcon = navIcon
    )
}