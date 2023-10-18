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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.LocalRepositoryProvider
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.theme.lightGrey
import com.kodeco.recipefinder.ui.widgets.BookmarkCard
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ShowBookmarks(viewModel: RecipeViewModel) {
  val scope = rememberCoroutineScope()
  val bookmarkListState = remember { mutableStateOf(listOf<Recipe>()) }
  LaunchedEffect(Unit) {
    scope.launch {
      viewModel.bookmarksState.collect { bookmarks ->
        bookmarkListState.value = bookmarks
      }
    }
    scope.launch {
      withContext(Dispatchers.IO) {
        viewModel.getBookmarks()
      }
    }
  }
  val bookmarks = bookmarkListState.value
  LazyColumn(content = {
    items(
      count = bookmarks.size,
      key = { index -> bookmarks[index].id },
      itemContent = { index ->
        val recipe = bookmarks[index]
        val currentItem by rememberUpdatedState(recipe)
        val dismissState = rememberDismissState(
          confirmValueChange = {
            scope.launch {
              withContext(Dispatchers.IO) {
                viewModel.deleteBookmark(currentItem)
              }
            }
            true
          }
        )
        SwipeToDismiss(state = dismissState, background = {
          val alignment = when (dismissState.dismissDirection) {
            DismissDirection.StartToEnd -> Alignment.CenterStart
            DismissDirection.EndToStart -> Alignment.CenterEnd
            null -> return@SwipeToDismiss
          }
          Box(
            Modifier
              .fillMaxSize()
              .background(lightGrey)
              .padding(horizontal = 20.dp),
            contentAlignment = alignment
          ) {
            Icon(
              Icons.Default.Delete,
              contentDescription = "Delete",
            )
          }
        }, dismissContent = {
          BookmarkCard(modifier = Modifier.fillMaxWidth(), recipe)
        })
      }
    )
  })
}


@Preview
@Composable
fun PreviewShowBookmarks() {
  val prefs = LocalPrefsProvider.current
  val repository = LocalRepositoryProvider.current
  Surface {
    Column {
      ShowBookmarks(RecipeViewModel(prefs, repository))
    }
  }
}
