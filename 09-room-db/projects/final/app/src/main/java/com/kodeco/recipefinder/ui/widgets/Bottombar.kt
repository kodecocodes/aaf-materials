package com.kodeco.recipefinder.ui.widgets

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kodeco.recipefinder.ui.theme.RecipeFinderTheme

@Composable
fun BottomBar(bottomItems: List<BottomItem>) {
  BottomNavigation(
    backgroundColor = MaterialTheme.colorScheme.primary,
  ) {

    bottomItems.forEach { item ->
      BottomNavigationItem(
        selected = item.selected,
        onClick = item.onclick,
        label = {
          Text(
            text = item.icon.contextText, style = TextStyle(
              color = MaterialTheme.colorScheme.onPrimary,
              fontSize = 12.sp
            )
          )
        },
        icon = {
          Icon(
            item.icon.icon,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = item.icon.contextText
          )
        })

    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  RecipeFinderTheme {
    BottomBar(
      listOf(
        BottomItem(icon = IconInfo(icon = Icons.Filled.Home, contextText = "Home")),
        BottomItem(icon = IconInfo(icon = Icons.Filled.AccountCircle, contextText = "Account"))
      )
    )
  }
}