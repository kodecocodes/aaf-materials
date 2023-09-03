package com.kodeco.recipefinder.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun CreateScaffold(toolbarInfo: ToolbarInfo? = null, bottomBarList: List<BottomItem>, content: @Composable (PaddingValues) -> Unit) {
  Scaffold(
      topBar = {
        CreateToolbar(toolbarInfo)
      },
      bottomBar = {
        BottomBar(bottomBarList)
      },
      content = content
  )
}
