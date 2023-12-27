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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.ui.theme.GroceryTitle
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel


@Composable
fun IngredientList(
  groceryListViewModel: GroceryListViewModel,
) {
  val allListState = rememberLazyListState()
  val needState = rememberLazyListState()
  val groceryUIState by groceryListViewModel.groceryUIState.collectAsState()
  val ingredients = groceryUIState.ingredients
  val checkBoxStates = groceryUIState.checkBoxes
  if (groceryUIState.allListShowing) {
    val currentIngredients =
      if (groceryUIState.searching) groceryUIState.searchIngredients else groceryUIState.ingredients
    LazyColumn(state = allListState, content = {
      items(
        count = currentIngredients.size,
        key = { index -> currentIngredients[index].id },
        itemContent = { index ->
          IngredientCard(
            modifier = Modifier.fillMaxWidth(),
            groceryListViewModel,
            currentIngredients[index],
            index,
            index % 2 == 0,
            groceryUIState.allListShowing
          )
        }
      )
    })
  } else {
    val needList = ingredients.filterIndexed { index, _ ->
      !checkBoxStates[index]
    }
    val haveList = ingredients.filterIndexed { index, _ ->
      checkBoxStates[index]
    }
    LazyColumn(state = needState, content = {
      item {
        Text(
          modifier = Modifier.padding(start = 16.dp, bottom = 12.dp),
          text = "Need", style = GroceryTitle
        )
      }
      items(
        count = needList.size,
        key = { index -> needList[index].id },
        itemContent = { index ->
          NeedIngredientCard(
            modifier = Modifier.fillMaxWidth(),
            index,
            true,
            ingredients
          )
        }
      )
      item {
        Text(
          modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 20.dp),
          text = "Have", style = GroceryTitle
        )
      }
      items(
        count = haveList.size,
        key = { index -> haveList[index].id },
        itemContent = { index ->
          NeedIngredientCard(
            modifier = Modifier.fillMaxWidth(),
            index,
            false,
            ingredients
          )
        }
      )
    })
  }
}

@Preview
@Composable
fun PreviewIngredientList() {
  Surface {
    IngredientList(groceryListViewModel = GroceryListViewModel())
  }
}