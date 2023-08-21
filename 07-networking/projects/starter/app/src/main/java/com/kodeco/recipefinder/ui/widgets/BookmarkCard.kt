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
            .fillMaxWidth().clickable {
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
