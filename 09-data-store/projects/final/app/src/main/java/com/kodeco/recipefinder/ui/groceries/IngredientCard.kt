package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.data.models.Ingredient
import com.kodeco.recipefinder.ui.theme.BodyLarge
import com.kodeco.recipefinder.ui.theme.iconBackgroundColor
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel


@Composable
fun IngredientCard(
  modifier: Modifier = Modifier,
  groceryListViewModel: GroceryListViewModel,
  ingredient: Ingredient,
  index: Int,
  isEven: Boolean,
  showCheckbox: Boolean,
) {
  val cardColor = if (isEven) CardDefaults.cardColors(containerColor = iconBackgroundColor) else
    CardDefaults.cardColors(containerColor = Color.White)
  val border = if (isEven) BorderStroke(width = 1.dp, color = Color.Black) else null
  val groceryUIState by groceryListViewModel.groceryUIState.collectAsState()
  val checkBoxStates = groceryUIState.checkBoxes
  val checked = checkBoxStates[index]
  Card(
    colors = cardColor,
    border = border,
    modifier = modifier
      .padding(horizontal = 16.dp)
      .fillMaxWidth(),
  ) {
    val style =
      if (checked) SpanStyle(textDecoration = TextDecoration.LineThrough) else SpanStyle()
    Row(modifier = Modifier.fillMaxSize()) {
      Text(
        text = AnnotatedString(ingredient.name, spanStyle = style),
        style = BodyLarge,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
      )
      Spacer(modifier = Modifier.weight(1f))
      if (showCheckbox) {
        Checkbox(checked = checked, onCheckedChange = {
          val updatedList = checkBoxStates.toMutableList()
          updatedList[index] = it
          groceryListViewModel.updateCheckList(updatedList)
        })
      }
    }
  }
}

@Preview
@Composable
fun PreviewIngredientCard() {
  val groceryListViewModel = GroceryListViewModel()
  groceryListViewModel.setIngredients(
    mutableListOf(
      Ingredient(
        id = 1,
        name = "Test",
        recipeId = 1,
        amount = 10.0
      )
    )
  )
  Surface {
    Row {
      IngredientCard(
        groceryListViewModel = groceryListViewModel,
        ingredient = Ingredient(id = 1, name = "Test", recipeId = 1, amount = 10.0),
        index = 0,
        isEven = false,
        showCheckbox = true
      )
    }
  }
}
