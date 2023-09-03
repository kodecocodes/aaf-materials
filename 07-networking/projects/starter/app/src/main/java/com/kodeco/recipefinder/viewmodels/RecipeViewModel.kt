package com.kodeco.recipefinder.viewmodels

import androidx.lifecycle.ViewModel
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.network.SpoonacularRecipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QueryState(
  val query: String = "",
  val offset: Int = 0,
  val number: Int = 20,
  val totalResults: Int = 0,
)

data class UIState(
  val searching: Boolean = false,
  val allChecked: Boolean = true,
  val bookmarksChecked: Boolean = false,
  val previousSearches: MutableList<String> = arrayListOf()
)

const val PAGE_SIZE = 20

// TODO: Add Prefs
class RecipeViewModel() : ViewModel() {
  companion object {
    const val PREVIOUS_SEARCH_KEY = "PREVIOUS_SEARCH_KEY"
  }

  // TODO: Add Service

  private val _recipeListState = MutableStateFlow<List<Recipe>>(listOf())
  val recipeListState = _recipeListState.asStateFlow()

  private val _recipeState = MutableStateFlow<SpoonacularRecipe?>(null)
  val recipeState = _recipeState.asStateFlow()

  private val _queryState = MutableStateFlow(QueryState())
  val queryState = _queryState.asStateFlow()

  private val _uiState = MutableStateFlow(UIState())
  val uiState = _uiState.asStateFlow()

  private val _bookmarksState = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
  val bookmarksState = _bookmarksState.asStateFlow()

  private val _ingredientsState = MutableStateFlow<MutableList<Ingredient>>(mutableListOf())
  val ingredientsState = _ingredientsState.asStateFlow()

  init {
    // TODO: Retrieve previous searches
  }

  fun queryRecipies(
    query: String,
    offset: Int,
    number: Int = PAGE_SIZE
  ) {
    // TODO: query recipes
  }

  private fun savePreviousSearches() {
    // TODO: Save previous searches
  }

  suspend fun queryRecipe(
    id: Int,
  ) {
    // TODO: Query a Recipe
  }

  // TODO: get Bookmarks
  suspend fun getBookmarks() {
  }

  // TODO: get Ingredients
  suspend fun getIngredients() {
  }

  // TODO: Get Bookmark
  suspend fun getBookmark() {
  }

  // TODO: bookmark recipe
  suspend fun bookmarkRecipe() {
  }

  // TODO: Add Previous Search
  fun addPreviousSearch(searchString: String) {
  }

  fun updateQueryState(queryState: QueryState) {
    _queryState.value = queryState
  }

  fun setSearching(searching: Boolean = true) {
    _uiState.value = _uiState.value.copy(searching = searching)
  }

  fun setAllChecked(allChecked: Boolean = true) {
    _uiState.value = _uiState.value.copy(allChecked = allChecked)
  }

  fun setBookmarksChecked(bookmarksChecked: Boolean = true) {
    _uiState.value = _uiState.value.copy(bookmarksChecked = bookmarksChecked)
  }

  // TODO: Delete Bookmark
  suspend fun deleteBookmark() {
  }

  // TODO: Delete Bookmark
  suspend fun deleteBookmark(recipeId: Int) {
  }
}