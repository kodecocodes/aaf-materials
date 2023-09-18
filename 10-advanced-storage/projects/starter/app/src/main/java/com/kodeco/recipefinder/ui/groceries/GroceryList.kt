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

package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.LocalRepositoryProvider
import com.kodeco.recipefinder.utils.viewModelFactory
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun GroceryList() {
  val prefs = LocalPrefsProvider.current
  val recipeViewModel: RecipeViewModel = viewModel(factory = viewModelFactory {
    RecipeViewModel(prefs)
  })
  val groceryListViewModel: GroceryListViewModel = viewModel()
  val repository = LocalRepositoryProvider.current
  val scope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
    scope.launch {
      recipeViewModel.ingredientsState.collect { ingredients ->
        groceryListViewModel.setIngredients(ingredients)
      }
    }
    scope.launch {
      recipeViewModel.getIngredients(repository)
    }
  }
  Column(modifier = Modifier.fillMaxSize()) {
    ShoppingImageRow()
    ShoppingSearchRow(groceryListViewModel)
    IngredientList(
      groceryListViewModel
    )
  }
}

@Preview
@Composable
fun PreviewGroceryList() {
  GroceryList()
}
