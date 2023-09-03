package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.recipefinder.LocalPrefsProvider
import com.kodeco.recipefinder.ui.theme.LabelLarge
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.RecipeViewModel


@Composable
fun ColumnScope.ChipRow(
  viewModel: RecipeViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .align(Alignment.CenterHorizontally),
    horizontalArrangement = Arrangement.Center
  ) {
    SpacerW4()
    FilterChip(selected = uiState.allChecked, leadingIcon = {
      if (uiState.allChecked) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
      }
    }, onClick = {
      val allChecked = !uiState.allChecked
      viewModel.setAllChecked(allChecked)
      if (allChecked) {
        viewModel.setBookmarksChecked(false)
      }
    }, label = { Text(text = "All", style = LabelLarge) })
    SpacerW4()
    FilterChip(selected = uiState.bookmarksChecked, leadingIcon = {
      if (uiState.bookmarksChecked) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
      }
    }, onClick = {
      val bookmarksChecked = !uiState.bookmarksChecked
      viewModel.setBookmarksChecked(bookmarksChecked)
      if (bookmarksChecked) {
        viewModel.setAllChecked(false)
      }
    }, label = { Text(text = "Bookmarks", style = LabelLarge) })
  }
}

@Preview
@Composable
fun PreviewChipRow() {
  val context = LocalContext.current
  val prefs = LocalPrefsProvider.current
  Surface {
    Column {
      ChipRow(RecipeViewModel(prefs))
    }
  }
}