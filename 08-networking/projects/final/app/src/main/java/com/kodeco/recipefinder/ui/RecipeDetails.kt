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
import com.kodeco.recipefinder.data.models.RecipeInformationResponse
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
  val recipeState = remember { mutableStateOf<RecipeInformationResponse?>(null) }
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
  recipe: RecipeInformationResponse,
  isBookmark: Boolean
) {
  // TODO: Add Repository
  val scope = rememberCoroutineScope()
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(lighterBlue)
  ) {
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
