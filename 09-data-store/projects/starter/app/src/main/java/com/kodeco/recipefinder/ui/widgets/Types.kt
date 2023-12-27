package com.kodeco.recipefinder.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

typealias Click = () -> Unit
typealias Actions = @Composable RowScope.() -> Unit
typealias ComposeFun = @Composable () -> Unit

val EmptyComposeFun: ComposeFun = {}