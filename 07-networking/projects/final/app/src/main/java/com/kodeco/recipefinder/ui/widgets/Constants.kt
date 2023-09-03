package com.kodeco.recipefinder.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

typealias ComposeWidget = @Composable () -> Unit
typealias RowComposeWidget = @Composable RowScope.() -> Unit

val SpacerW4: ComposeWidget = {
  Spacer(modifier = Modifier.width(4.dp))
}
val SpacerW8: ComposeWidget = {
  Spacer(modifier = Modifier.width(8.dp))
}
val SpacerW12: ComposeWidget = {
  Spacer(modifier = Modifier.width(12.dp))
}
val SpacerW16: ComposeWidget = {
  Spacer(modifier = Modifier.width(16.dp))
}
val SpacerMax: RowComposeWidget = {
  Spacer(modifier = Modifier.weight(1f))
}
