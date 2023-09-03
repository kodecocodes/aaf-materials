package com.kodeco.recipefinder.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.theme.BodyLarge
import timber.log.Timber

@Composable
fun RecipeCard(modifier: Modifier = Modifier, recipe: Recipe) {
  Card(
    colors = CardDefaults.cardColors(containerColor = Color.White),
    modifier = modifier
      .padding(4.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      recipe.image?.let { image ->
        AsyncImage(
          modifier = Modifier.fillMaxWidth(),
          model = buildRecipeImageBuilder(image),
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
      Text(
        text = recipe.title,
        style = BodyLarge,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Composable
fun buildRecipeImageBuilder(image: String): ImageRequest {
  return ImageRequest.Builder(LocalContext.current)
    .data(image)
    .crossfade(true)
    .networkCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .diskCacheKey(image)
    .memoryCacheKey(image)
    .build()
}