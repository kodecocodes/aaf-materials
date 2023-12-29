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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.ui.theme.transparent
import com.kodeco.recipefinder.ui.widgets.SpacerMax
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun SearchRow(
  viewModel: RecipeViewModel
) {
  var expanded by remember { mutableStateOf(false) }
  var searchText by remember {
    mutableStateOf("")
  }
  val uiState by viewModel.uiState.collectAsState()
  val keyboard = LocalSoftwareKeyboardController.current

  Row(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth()
  ) {
    Box(modifier = Modifier
      .align(Alignment.CenterVertically)
      .clickable {
        if (searchText.isNotEmpty()) {
          viewModel.setSearching(true)
          keyboard?.hide()
          searchRecipes(searchText.trim(), viewModel)
        }
      }) {
      Icon(
        Icons.Filled.Search,
        contentDescription = "Search",
      )
    }
    SpacerW4()
    TextField(
      modifier = Modifier.fillMaxWidth(0.8f),
      value = searchText,
      onValueChange = {
        searchText = it
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(
        onSearch = {
          viewModel.setSearching(true)
          keyboard?.hide()
          searchRecipes(searchText.trim(), viewModel)
        },
      ),
      singleLine = true,
      colors = TextFieldDefaults.colors(
        focusedContainerColor = transparent,
        unfocusedContainerColor = transparent,
        focusedIndicatorColor = transparent,
        unfocusedIndicatorColor = transparent,
      ),
      label = { Text("Search...") },
    )
    SpacerMax()
    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
      IconButton(onClick = {
        searchText = ""
      }) {
        Icon(Icons.Filled.Clear, contentDescription = "Filter")
      }
    }
    Box {
      // 3 vertical dots icon
      IconButton(onClick = {
        expanded = true
      }) {
        Icon(
          imageVector = Icons.Default.MoreVert,
          contentDescription = "Open Options"
        )
      }
      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        uiState.previousSearches.forEach {
          DropdownMenuItem(
            text = { Text(it) },
            onClick = {
              expanded = false
              searchText = it
            }
          )
        }
      }
    }
  }
}


@Preview
@Composable
fun PreviewSearchRow() {
  val prefs = LocalPrefsProvider.current
  // TODO: Add Repository
  Surface {
    Column {
      SearchRow(RecipeViewModel(prefs))
    }
  }
}
