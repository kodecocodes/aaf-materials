package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ColumnScope.Progress() {
  CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
}


@Preview
@Composable
fun PreviewProgress() {
  Surface {
    Column {
      Progress()
    }
  }
}