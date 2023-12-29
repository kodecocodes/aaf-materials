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

package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.LocalNavigatorProvider
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.widgets.RecipeCard
import com.kodeco.recipefinder.viewmodels.PAGE_SIZE
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun ColumnScope.ShowRecipeList(
  recipes: MutableState<List<Recipe>>,
  viewModel: RecipeViewModel
) {
  val navController = LocalNavigatorProvider.current
  val queryState by viewModel.queryState.collectAsState()
  val uiState by viewModel.uiState.collectAsState()
  val lazyGridState: LazyGridState = rememberLazyGridState()
  val loadMore = remember(lazyGridState) {
    derivedStateOf {
      val lastVisibleIndex =
        lazyGridState.firstVisibleItemIndex + lazyGridState.layoutInfo.visibleItemsInfo.size
      lastVisibleIndex + PAGING_OFFSET > recipes.value.size &&
          lastVisibleIndex < queryState.totalResults
    }
  }
  var firstTime by remember {
    mutableStateOf(true)
  }
  LaunchedEffect(loadMore) {
    val offset = if (firstTime) 0 else queryState.offset + PAGE_SIZE
    firstTime = false
    viewModel.updateQueryState(queryState.copy(offset = offset))
    searchRecipes(queryState.query, viewModel)
  }
  if (uiState.searching) {
    Progress()
  } else {
    LazyVerticalGrid(
      state = lazyGridState,
      modifier = Modifier.fillMaxSize(),
      columns = GridCells.Adaptive(124.dp),
      // content padding
      contentPadding = PaddingValues(
        start = 12.dp,
        top = 16.dp,
        end = 12.dp,
        bottom = 16.dp
      ),
      content = {
        itemsIndexed(recipes.value, key = { _, item ->
          item.id
        }) { _, item ->
          RecipeCard(
            modifier = Modifier.clickable {
              navController.navigate("details/${item.id}")
            },
            item
          )
        }
      }
    )
  }
}


@Preview
@Composable
fun PreviewShowRecipeList() {
  val prefs = LocalPrefsProvider.current
  // TODO: Add Repository
  val recipeListState = remember { mutableStateOf(listOf<Recipe>()) }
  Surface {
    Column {
      ShowRecipeList(recipeListState, RecipeViewModel(prefs))
    }
  }
}
