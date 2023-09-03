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