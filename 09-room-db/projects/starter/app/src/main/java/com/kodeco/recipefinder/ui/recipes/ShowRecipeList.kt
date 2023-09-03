package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.LocalNavigatorProvider
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.widgets.RecipeCard
import com.kodeco.recipefinder.viewmodels.PAGE_SIZE
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun ColumnScope.ShowRecipeList(
    recipes: MutableState<List<Recipe>>,
    viewModel: RecipeViewModel
) {
  val navController = LocalNavigatorProvider.current
  val queryState by viewModel.queryState.collectAsState()
  val uiState by viewModel.uiState.collectAsState()
  val lazyGridState: LazyGridState = rememberLazyGridState()
  val loadMore = remember(lazyGridState) {
    derivedStateOf {
      val lastVisibleIndex =
          lazyGridState.firstVisibleItemIndex + lazyGridState.layoutInfo.visibleItemsInfo.size
      lastVisibleIndex + PAGING_OFFSET > recipes.value.size &&
          lastVisibleIndex < queryState.totalResults
    }
  }
  var firstTime by remember {
    mutableStateOf(true)
  }
  LaunchedEffect(loadMore) {
    val offset = if (firstTime) 0 else queryState.offset + PAGE_SIZE
    firstTime = false
    viewModel.updateQueryState(queryState.copy(offset = offset))
    searchRecipes(queryState.query, viewModel)
  }
  if (uiState.searching) {
    Progress()
  } else {
    LazyVerticalGrid(
        state = lazyGridState,
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(124.dp),
        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
          itemsIndexed(recipes.value, key = { _, item ->
            item.id
          }) { _, item ->
            RecipeCard(
                modifier = Modifier.clickable {
                  navController.navigate("details/${item.id}")
                },
                item
            )
          }
        }
    )
  }
}


@Preview
@Composable
fun PreviewShowRecipeList() {
  val context = LocalContext.current
  val prefs = LocalPrefsProvider.current
  val recipeListState = remember { mutableStateOf(listOf<Recipe>()) }
  Surface {
    Column {
      ShowRecipeList(recipeListState, RecipeViewModel(prefs))
    }
  }
}