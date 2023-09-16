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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel


@Composable
fun RowScope.FilterIcon(groceryListViewModel: GroceryListViewModel) {
  val groceryUIState by groceryListViewModel.groceryUIState.collectAsState()
  val allListShowing = groceryUIState.allListShowing
  val expanded = remember { mutableStateOf(false) }
  Box(modifier = Modifier.align(Alignment.CenterVertically)) {
    IconButton(onClick = {
      expanded.value = !expanded.value
    }) {
      Icon(Icons.Filled.FilterAlt, contentDescription = "Filter")
    }
    SpacerW4()
    DropdownMenu(
      expanded = expanded.value,
      onDismissRequest = { expanded.value = false }
    ) {
      DropdownMenuItem(
        text = { Text("All") },
        leadingIcon = {
          if (allListShowing) Icon(
            imageVector = Icons.Filled.CheckBox,
            contentDescription = "All"
          )
        },
        onClick = {
          groceryListViewModel.setAllShowing(true)
          expanded.value = false
        }
      )
      DropdownMenuItem(
        text = { Text("Need/Have") },
        leadingIcon = {
          if (!allListShowing) Icon(
            imageVector = Icons.Filled.CheckBox,
            contentDescription = "Need"
          )
        },
        onClick = {
          groceryListViewModel.setAllShowing(false)
          expanded.value = false
        }
      )
    }
  }
}


@Preview
@Composable
fun PreviewFilterIcon() {
  Surface {
    Row {
      FilterIcon(GroceryListViewModel())
    }
  }
}