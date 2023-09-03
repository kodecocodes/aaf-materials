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
  // TODO: Add Repository
  val scope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
    scope.launch {
      recipeViewModel.ingredientsState.collect { ingredients ->
        groceryListViewModel.setIngredients(ingredients)
      }
    }
    // TODO: Get Ingredients
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
