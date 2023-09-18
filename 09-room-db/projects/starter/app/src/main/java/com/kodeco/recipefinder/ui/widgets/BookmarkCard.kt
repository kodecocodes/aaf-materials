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

package com.kodeco.recipefinder.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.kodeco.recipefinder.LocalNavigatorProvider
import com.kodeco.recipefinder.R
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.theme.BodyLarge
import timber.log.Timber

@Composable
fun BookmarkCard(modifier: Modifier = Modifier, recipe: Recipe) {
  val navController = LocalNavigatorProvider.current
  Card(
    colors = CardDefaults.cardColors(containerColor = Color.White),
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth()
      .clickable {
        navController.navigate("bookmarks/${recipe.id}")
      },
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
  ) {
    Row(modifier = Modifier.fillMaxSize()) {
      SpacerW12()
      recipe.image?.let { image ->
        AsyncImage(
          modifier = Modifier
            .width(68.dp)
            .height(68.dp),
          model = buildRecipeImageBuilder(image),
          contentScale = ContentScale.FillWidth,
          onState = { state ->
            if (state is AsyncImagePainter.State.Error) {
              Timber.e(
                state.result.throwable,
                "Problems loading image $image"
              )
            }
          },
          contentDescription = null,
        )
      }
      SpacerW12()
      Text(
        text = recipe.title,
        style = BodyLarge,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
      )
      Spacer(modifier = Modifier.weight(1f))
      IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
        navController.navigate("bookmarks/${recipe.id}")
      }) {
        Icon(
          imageVector = ImageVector.vectorResource(
            R.drawable.arrow_circle_right
          ), tint = Color.Black, contentDescription = null
        )
      }
    }
  }
}
