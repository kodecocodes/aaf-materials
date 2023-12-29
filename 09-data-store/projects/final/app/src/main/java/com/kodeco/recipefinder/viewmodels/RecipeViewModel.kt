package com.kodeco.recipefinder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.recipefinder.data.Prefs
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.network.RetrofitInstance
import com.kodeco.recipefinder.network.SpoonacularRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

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

class RecipeViewModel(private val prefs: Prefs) : ViewModel() {
  companion object {
    const val PREVIOUS_SEARCH_KEY = "PREVIOUS_SEARCH_KEY"
  }

  private val spoonacularService = RetrofitInstance.spoonacularService

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
    viewModelScope.launch {
      val previousSearchString = prefs.getString(PREVIOUS_SEARCH_KEY)
      if (!previousSearchString.isNullOrEmpty()) {
        val storedList = previousSearchString.split(",")
        _uiState.value = _uiState.value.copy(previousSearches = storedList.toMutableList())
      }
    }
  }

  fun queryRecipies(
    query: String,
    offset: Int,
    number: Int = PAGE_SIZE
  ) {
    viewModelScope.launch {
      try {
        val response = spoonacularService.queryRecipes(query, offset, number)
        _recipeListState.value = response.recipes
        _queryState.value =
          QueryState(query, offset, number, response.totalResults)
      } catch (e: Exception) {
        Timber.e(e, "Problems getting Recipes")
        _recipeListState.value = listOf()
      }
    }
  }

  private fun savePreviousSearches() {
    viewModelScope.launch {
      val searchString = _uiState.value.previousSearches.joinToString(",")
      prefs.saveString(PREVIOUS_SEARCH_KEY, searchString)
    }
  }

  suspend fun queryRecipe(
    id: Int,
  ) {
    viewModelScope.launch(Dispatchers.Default) {
      try {
        val spoonacularRecipe = spoonacularService.queryRecipe(id)
        _recipeState.value = spoonacularRecipe
      } catch (e: Exception) {
        Timber.e(e, "Problems getting Recipe for id $id")
        _recipeState.value = null
      }
    }
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

  fun addPreviousSearch(searchString: String) {
    if (!_uiState.value.previousSearches.contains(searchString)) {
      val updatedSearches = mutableListOf<String>()
      // 2
      updatedSearches.addAll(uiState.value.previousSearches)
      updatedSearches.add(searchString)
      // 3
      _uiState.value = _uiState.value.copy(previousSearches = updatedSearches)
      // 4
      savePreviousSearches()
    }
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
