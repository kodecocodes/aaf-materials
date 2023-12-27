package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.R
import com.kodeco.recipefinder.ui.theme.background1Color


@Composable
fun ShoppingImageRow() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp)
      .background(background1Color)
  ) {
    Image(
      modifier = Modifier.align(Alignment.Center),
      painter = painterResource(id = R.drawable.background1),
      contentDescription = null,
      contentScale = ContentScale.FillWidth
    )
  }
}

@Preview
@Composable
fun PreviewShoppingImageRow() {
  Surface {
    ShoppingImageRow()
  }
}
