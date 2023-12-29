package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.ui.theme.transparent
import com.kodeco.recipefinder.ui.widgets.SpacerMax
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.utils.rememberState
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel


@Composable
fun ShoppingSearchRow(
  groceryListViewModel: GroceryListViewModel,
) {
  val searchText = rememberState {
    ""
  }
  val keyboard = LocalSoftwareKeyboardController.current
  val groceryUIState by groceryListViewModel.groceryUIState.collectAsState()
  val ingredients = groceryUIState.ingredients
  fun startSearch(searchString: String) {
    val searchIngredients = ingredients.filter { it.name.contains(searchString) }
    groceryListViewModel.setSearchIngredients(searchIngredients)
  }
  Row(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth()
  ) {
    Box(modifier = Modifier
      .align(Alignment.CenterVertically)
      .clickable {
        if (searchText.value.isNotEmpty()) {
          startSearch(searchText.value.trim())
          keyboard?.hide()
        }
      }) {
      Icon(
        Icons.Filled.Search,
        contentDescription = "Search",
      )
    }
    SpacerW4()
    TextField(
      modifier = Modifier.weight(1f),
      value = searchText.value,
      onValueChange = {
        searchText.value = it
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      keyboardActions = KeyboardActions(
        onSearch = {
          if (searchText.value.isNotEmpty()) {
            startSearch(searchText.value.trim())
            keyboard?.hide()
          }
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
        searchText.value = ""
        groceryListViewModel.setSearching(false)
      }) {
        Icon(Icons.Filled.Clear, contentDescription = "Filter")
      }
    }
    FilterIcon(groceryListViewModel)
  }
}

@Preview
@Composable
fun PreviewShoppingSearchRow() {
  Surface {
    ShoppingSearchRow(groceryListViewModel = GroceryListViewModel())
  }
}
