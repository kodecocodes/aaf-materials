package com.kodeco.recipefinder.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kodeco.recipefinder.R
import com.kodeco.recipefinder.ui.groceries.GroceryList
import com.kodeco.recipefinder.ui.recipes.RecipeList
import com.kodeco.recipefinder.ui.widgets.BottomItem
import com.kodeco.recipefinder.ui.widgets.CreateScaffold
import com.kodeco.recipefinder.ui.widgets.IconInfo
import kotlinx.coroutines.launch

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

@Composable
fun MainScreen() {
  val selectedIndex = remember {
    mutableIntStateOf(0)
  }
  // TODO: Add Prefs
  val scope = rememberCoroutineScope()
  LaunchedEffect(Unit) {
    scope.launch {
      // TODO: Get screen position from prefs
    }
  }
  CreateScaffold(
    bottomBarList = listOf(
      BottomItem(selected = selectedIndex.intValue == 0, icon = IconInfo(
        icon = ImageVector.vectorResource(
          R.drawable.icon_recipe
        ), contextText = "Recipes"
      ), onclick = {
        selectedIndex.intValue = 0
        // TODO: Save screen position to prefs
      }),
      BottomItem(selected = selectedIndex.intValue == 1, icon = IconInfo(
        icon = ImageVector.vectorResource(
          R.drawable.shopping_cart
        ), contextText = "Groceries"
      ), onclick = {
        selectedIndex.intValue = 1
        // TODO: Save screen position to prefs
      }),
    ), content = { padding ->
      Box(modifier = Modifier.padding(padding)) {
        when (selectedIndex.intValue) {
          0 -> RecipeList()
          1 -> GroceryList()
        }
      }
    }

  )
}