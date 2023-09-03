package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.R
import com.kodeco.recipefinder.ui.theme.lightGreen
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun ImageRow() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp)
      .background(lightGreen)
  ) {
    Image(
      modifier = Modifier.align(Alignment.Center),
      painter = painterResource(id = R.drawable.background2),
      contentDescription = null
    )
  }
}

fun searchRecipes(searchString: String, viewModel: RecipeViewModel) {
  if (searchString.isNotEmpty()) {
    viewModel.addPreviousSearch(searchString)
    viewModel.queryRecipies(searchString, viewModel.queryState.value.offset)
  } else {
    viewModel.setSearching(false)
  }
}


@Preview
@Composable
fun PreviewImageRow() {
  Surface {
    ImageRow()
  }
}