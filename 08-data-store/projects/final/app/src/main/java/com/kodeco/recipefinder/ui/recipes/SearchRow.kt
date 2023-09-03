package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.ui.theme.transparent
import com.kodeco.recipefinder.ui.widgets.SpacerMax
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun SearchRow(
    viewModel: RecipeViewModel
) {
  var expanded by remember { mutableStateOf(false) }
  var searchText by remember {
    mutableStateOf("")
  }
  val uiState by viewModel.uiState.collectAsState()
  val keyboard = LocalSoftwareKeyboardController.current

  Row(
      modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth()
  ) {
    Box(modifier = Modifier
        .align(Alignment.CenterVertically)
        .clickable {
            if (searchText.isNotEmpty()) {
                viewModel.setSearching(true)
                keyboard?.hide()
                searchRecipes(searchText.trim(), viewModel)
            }
        }) {
      Icon(
          Icons.Filled.Search,
          contentDescription = "Search",
      )
    }
    SpacerW4()
    TextField(
        modifier = Modifier.fillMaxWidth(0.8f),
        value = searchText,
        onValueChange = {
          searchText = it
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
              viewModel.setSearching(true)
              keyboard?.hide()
              searchRecipes(searchText.trim(), viewModel)
            },
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = transparent,
            unfocusedContainerColor = transparent,
            focusedIndicatorColor = transparent,
            unfocusedIndicatorColor = transparent,
        ),
        label = { Text("Search...") },
    )
    SpacerMax()
    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
      IconButton(onClick = {
        searchText = ""
      }) {
        Icon(Icons.Filled.Clear, contentDescription = "Filter")
      }
    }
    Box {
      // 3 vertical dots icon
      IconButton(onClick = {
        expanded = true
      }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Open Options"
        )
      }
      DropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false }
      ) {
        uiState.previousSearches.forEach {
          DropdownMenuItem(
              text = { Text(it) },
              onClick = {
                expanded = false
                searchText = it
              }
          )
        }
      }
    }
  }
}


@Preview
@Composable
fun PreviewSearchRow() {
  val context = LocalContext.current
  val prefs = LocalPrefsProvider.current
  Surface {
    Column {
      SearchRow(RecipeViewModel(prefs))
    }
  }
}
