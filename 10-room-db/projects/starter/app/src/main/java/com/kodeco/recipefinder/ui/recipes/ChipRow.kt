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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.ui.theme.LabelLarge
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun ColumnScope.ChipRow(
  viewModel: RecipeViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .align(Alignment.CenterHorizontally),
    horizontalArrangement = Arrangement.Center
  ) {
    SpacerW4()
    FilterChip(selected = uiState.allChecked, leadingIcon = {
      if (uiState.allChecked) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
      }
    }, onClick = {
      val allChecked = !uiState.allChecked
      viewModel.setAllChecked(allChecked)
      if (allChecked) {
        viewModel.setBookmarksChecked(false)
      }
    }, label = { Text(text = "All", style = LabelLarge) })
    SpacerW4()
    FilterChip(selected = uiState.bookmarksChecked, leadingIcon = {
      if (uiState.bookmarksChecked) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
      }
    }, onClick = {
      val bookmarksChecked = !uiState.bookmarksChecked
      viewModel.setBookmarksChecked(bookmarksChecked)
      if (bookmarksChecked) {
        viewModel.setAllChecked(false)
      }
    }, label = { Text(text = "Bookmarks", style = LabelLarge) })
  }
}

@Preview
@Composable
fun PreviewChipRow() {
  val prefs = LocalPrefsProvider.current
  // TODO: Add Repository
  Surface {
    Column {
      ChipRow(RecipeViewModel(prefs))
    }
  }
}
