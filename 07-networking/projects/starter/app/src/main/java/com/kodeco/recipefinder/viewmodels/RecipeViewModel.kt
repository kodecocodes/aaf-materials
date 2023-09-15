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

package com.kodeco.recipefinder.viewmodels

import androidx.lifecycle.ViewModel
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.data.models.RecipeInformationResponse
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

  private val _recipeState = MutableStateFlow<RecipeInformationResponse?>(null)
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