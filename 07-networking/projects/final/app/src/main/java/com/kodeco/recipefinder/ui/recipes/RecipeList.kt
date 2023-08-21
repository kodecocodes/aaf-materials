package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.utils.viewModelFactory
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch

const val PAGING_OFFSET = 6

@Composable
fun RecipeList() {
    // TODO: Add Prefs
    // TODO: Update RecipeModel
    val viewModel: RecipeViewModel = viewModel(factory = viewModelFactory {
        RecipeViewModel()
    })
    val recipeListState = remember { mutableStateOf(listOf<Recipe>()) }
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.queryState.collect { state ->
                if (state.number > 0) {
                    viewModel.setSearching(false)
                }
            }
        }
        scope.launch {
            viewModel.recipeListState.collect { state ->
                recipeListState.value = state
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ImageRow()
        ChipRow(viewModel)
        if (uiState.allChecked) {
            SearchRow(viewModel)
        }
        if (uiState.allChecked) {
            ShowRecipeList(recipeListState, viewModel)
        } else {
            ShowBookmarks(viewModel)
        }
    }
}

@Preview
@Composable
fun PreviewRecipeList() {
    Surface {
        RecipeList()
    }
}