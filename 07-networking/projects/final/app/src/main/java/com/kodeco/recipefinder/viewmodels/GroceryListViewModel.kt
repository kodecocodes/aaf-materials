package com.kodeco.recipefinder.viewmodels

import androidx.lifecycle.ViewModel
import com.kodeco.recipefinder.data.models.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GroceryUIState(
  val allListShowing: Boolean = true,
  val searching: Boolean = false,
  val ingredients: List<Ingredient> = listOf(),
  val searchIngredients: List<Ingredient> = listOf(),
  val checkBoxes: List<Boolean> = listOf()
)

class GroceryListViewModel : ViewModel() {
  private val _groceryUIState: MutableStateFlow<GroceryUIState> =
    MutableStateFlow(GroceryUIState())
  val groceryUIState = _groceryUIState.asStateFlow()

  fun setAllShowing(showing: Boolean) {
    _groceryUIState.value = _groceryUIState.value.copy(allListShowing = showing)
  }

  fun setSearching(searching: Boolean) {
    _groceryUIState.value = _groceryUIState.value.copy(searching = searching)
  }

  fun setSearchIngredients(searchIngredients: List<Ingredient>) {
    _groceryUIState.value = _groceryUIState.value.copy(
      searchIngredients = searchIngredients,
      searching = searchIngredients.isNotEmpty()
    )
  }

  fun setIngredients(ingredients: MutableList<Ingredient>) {
    _groceryUIState.value = _groceryUIState.value.copy(ingredients = ingredients,
      checkBoxes =
      MutableList(ingredients.size) {
        false
      }
    )
  }

  fun updateCheckList(updatedList: MutableList<Boolean>) {
    _groceryUIState.value = _groceryUIState.value.copy(checkBoxes = updatedList)
  }

}