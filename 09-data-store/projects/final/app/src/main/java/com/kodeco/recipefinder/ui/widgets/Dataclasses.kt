package com.kodeco.recipefinder.ui.widgets

import androidx.compose.ui.graphics.vector.ImageVector

data class IconInfo(val icon: ImageVector, val contextText: String = "")
data class BottomItem(val selected: Boolean = false, val onclick: Click = {}, val icon: IconInfo)
data class NavInfo(val icon: IconInfo, val navOnClick: Click = {})
data class ToolbarInfo(val title: String, val navInfo: NavInfo? = null, val actions: Actions = {})
