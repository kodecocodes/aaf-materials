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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.utils.viewModelFactory
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch

const val PAGING_OFFSET = 6

@Composable
fun RecipeList() {
  // TODO: Add Prefs
  // TODO: Update RecipeModel
  val viewModel: RecipeViewModel = viewModel(factory = viewModelFactory {
    RecipeViewModel()
  })
  val recipeListState = remember { mutableStateOf(listOf<Recipe>()) }
  val scope = rememberCoroutineScope()
  val uiState by viewModel.uiState.collectAsState()
  LaunchedEffect(Unit) {
    scope.launch {
      viewModel.queryState.collect { state ->
        if (state.number > 0) {
          viewModel.setSearching(false)
        }
      }
    }
    scope.launch {
      viewModel.recipeListState.collect { state ->
        recipeListState.value = state
      }
    }
  }
  Column(modifier = Modifier.fillMaxSize()) {
    ImageRow()
    ChipRow(viewModel)
    if (uiState.allChecked) {
      SearchRow(viewModel)
    }
    if (uiState.allChecked) {
      ShowRecipeList(recipeListState, viewModel)
    } else {
      ShowBookmarks(viewModel)
    }
  }
}

@Preview
@Composable
fun PreviewRecipeList() {
  Surface {
    RecipeList()
  }
}