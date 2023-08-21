package com.kodeco.recipefinder.ui

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.kodeco.recipefinder.LocalNavigatorProvider
import com.kodeco.recipefinder.R
import com.kodeco.recipefinder.network.SpoonacularRecipe
import com.kodeco.recipefinder.ui.theme.HeadlineSmall
import com.kodeco.recipefinder.ui.theme.lighterBlue
import com.kodeco.recipefinder.ui.widgets.buildRecipeImageBuilder
import com.kodeco.recipefinder.utils.viewModelFactory
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RecipeDetails(recipeId: Int? = null, databaseRecipeId: Int? = null) {
    val scope = rememberCoroutineScope()
    // TODO: Add Prefs
    val viewModel: RecipeViewModel = viewModel(factory = viewModelFactory {
        // TODO: Add Prefs
        RecipeViewModel()
    })
    val recipeState = remember { mutableStateOf<SpoonacularRecipe?>(null) }
    val navController = LocalNavigatorProvider.current
    // TODO: Add Repository
    if (recipeId != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.recipeState.collect { state ->
                    recipeState.value = state
                }
            }
            scope.launch {
                viewModel.queryRecipe(recipeId)
            }
        }
    } else if (databaseRecipeId != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.recipeState.collect { state ->
                    recipeState.value = state
                }
            }
            scope.launch {
                // TODO: Add Repository
                viewModel.getBookmark()
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (recipeState.value != null) {
            recipeState.value?.let { recipe ->
                Box(modifier = Modifier.height(200.dp)) {
                    if (recipe.image != null) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = buildRecipeImageBuilder(recipe.image),
                            contentScale = ContentScale.FillWidth,
                            onState = { state ->
                                if (state is AsyncImagePainter.State.Error) {
                                    Timber.e(
                                        state.result.throwable,
                                        "Problems loading image ${recipe.image}"
                                    )
                                }
                            },
                            contentDescription = null,
                        )
                    }
                    TitleRow(
                        modifier = Modifier.align(Alignment.BottomStart),
                        navController,
                        viewModel,
                        recipe,
                        databaseRecipeId != null
                    )
                }
                Description(description = recipe.summary)
            }
        }
    }
}

@Composable
fun TitleRow(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RecipeViewModel,
    recipe: SpoonacularRecipe,
    isBookmark: Boolean
) {
    // TODO: Add Repository
    val scope = rememberCoroutineScope()
    Row(modifier = modifier
        .fillMaxWidth()
        .background(lighterBlue)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.Black,
                contentDescription = "Go back"
            )
        }
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = recipe.title,
            style = HeadlineSmall.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            scope.launch {
                if (isBookmark) {
                    // TODO: Add Repository
                    viewModel.deleteBookmark()
                } else {
                    // TODO: Add Repository
                    viewModel.bookmarkRecipe()
                }
            }
            navController.popBackStack()
        }) {
            val imageVector = ImageVector.vectorResource(
                if (isBookmark) R.drawable.icon_bookmark_filled else R.drawable.icon_bookmark
            )
            Icon(
                imageVector = imageVector, tint = Color.Black, contentDescription = null
            )
        }
    }
}

@Composable
fun Description(modifier: Modifier = Modifier, description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        AndroidView(modifier = modifier.padding(16.dp), factory = {
            TextView(it)
        }, update = {
            it.text = HtmlCompat.fromHtml(description, 0)
        })
    }
}
