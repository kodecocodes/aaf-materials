/*
 * Copyright (c) 2023 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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