package com.kodeco.recipefinder.ui.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.recipefinder.data.models.Recipe
import com.kodeco.recipefinder.ui.theme.lightGrey
import com.kodeco.recipefinder.ui.widgets.BookmarkCard
import com.kodeco.recipefinder.viewmodels.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ShowBookmarks(viewModel: RecipeViewModel) {
  val scope = rememberCoroutineScope()
  // TODO: Add Repository
  val bookmarkListState = remember { mutableStateOf(listOf<Recipe>()) }
  LaunchedEffect(Unit) {
    scope.launch {
      viewModel.bookmarksState.collect { bookmarks ->
        bookmarkListState.value = bookmarks
      }
    }
    scope.launch {
      withContext(Dispatchers.IO) {
        // TODO: Add Repository
        viewModel.getBookmarks()
      }
    }
  }
  val bookmarks = bookmarkListState.value
  LazyColumn(content = {
    items(
        count = bookmarks.size,
        key = { index -> bookmarks[index].id },
        itemContent = { index ->
          val recipe = bookmarks[index]
          val currentItem by rememberUpdatedState(recipe)
          val dismissState = rememberDismissState(
              confirmValueChange = {
                scope.launch {
                  withContext(Dispatchers.IO) {
                    // TODO: Add Repository
                    viewModel.deleteBookmark()
                  }
                }
                true
              }
          )
          SwipeToDismiss(state = dismissState, background = {
            val alignment = when (dismissState.dismissDirection) {
              DismissDirection.StartToEnd -> Alignment.CenterStart
              DismissDirection.EndToStart -> Alignment.CenterEnd
              null -> return@SwipeToDismiss
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(lightGrey)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
              Icon(
                  Icons.Default.Delete,
                  contentDescription = "Delete",
              )
            }
          }, dismissContent = {
            BookmarkCard(modifier = Modifier.fillMaxWidth(), recipe)
          })
        }
    )
  })
}


@Preview
@Composable
fun PreviewShowBookmarks() {
  val context = LocalContext.current
  Surface {
    Column {
      // TODO: Add PRefs
      ShowBookmarks(RecipeViewModel())
    }
  }
}