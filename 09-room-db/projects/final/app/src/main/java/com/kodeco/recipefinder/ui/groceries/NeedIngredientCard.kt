package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.ui.theme.BodyLarge

@Composable
fun NeedIngredientCard(
  modifier: Modifier = Modifier,
  index: Int,
  need: Boolean,
  ingredients: List<Ingredient>
) {
  val cardColor = CardDefaults.cardColors(containerColor = Color.White)
  val border = if (need) BorderStroke(width = 1.dp, color = Color.Black) else null
  val ingredient = ingredients[index]
  Card(
    colors = cardColor,
    border = border,
    modifier = modifier
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .fillMaxWidth(),
  ) {
    val style =
      if (!need) SpanStyle(textDecoration = TextDecoration.LineThrough) else SpanStyle()
    Row(modifier = Modifier.fillMaxSize()) {
      Text(
        text = AnnotatedString(ingredient.name, spanStyle = style),
        style = BodyLarge,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
      )
      Spacer(modifier = Modifier.weight(1f))
    }
  }
}

@Preview
@Composable
fun PreviewNeedIngredientCard() {
  Surface {
    NeedIngredientCard(
      index = 0, need = true, ingredients = mutableListOf(
        Ingredient(
          id = 1,
          name = "Test",
          recipeId = 1,
          amount = 10.0
        )
      )
    )
  }

}