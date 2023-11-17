/*
 * Copyright (c) 2023 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.kodeco.chat.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kodeco.chat.R
import com.kodeco.chat.components.KodecochatAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationContent() {

  val scrollState = rememberLazyListState()
  val scope = rememberCoroutineScope()
  val messages  = listOf("message 1", "message 2", "message 3")
  val topBarState = rememberTopAppBarState()
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

  Surface() {
    Box(modifier = Modifier.fillMaxSize()) {
      Column(
        Modifier
          .fillMaxSize()
//          .nestedScroll(scrollBehavior.nestedScrollConnection)
      ) {
        Messages(
          messages,
          modifier = Modifier.weight(1f)
//          scrollState = scrollState
        )
        SimpleUserInput()
      }
      // Channel name bar floats above the messages
      ChannelNameBar(channelName = "Android Apprentice")
    }
  }


}

@Composable
fun SimpleUserInput() {
  val context = LocalContext.current
  var chatInputText by remember { mutableStateOf("") }
  var chatOutputText by remember { mutableStateOf(context.getString(R.string.chat_display_default)) }
  Text(text = chatOutputText)

  Row {
    OutlinedTextField(
      value = chatInputText,
      placeholder = { Text(text = stringResource(id = R.string.chat_entry_default)) },
      onValueChange = {
        chatInputText = it
      },
    )

    Button(onClick = {
      chatOutputText = chatInputText
      chatInputText = ""
    }) {
      Text(text = stringResource(id = R.string.send_button))
    }

  }
}

@Composable
fun Messages(
  messages: List<String>,
//  scrollState: LazyListState,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    LazyColumn(
      reverseLayout = true,
//      state = scrollState,
      // Add content padding so that the content can be scrolled (y-axis)
      // below the status bar + app bar
      // TODO: Get height from somewhere
      contentPadding =
      WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
      modifier = Modifier
        .fillMaxSize()
    ) {

      item {
        Text(text = "First message")
      }
      item {
        Text(text = "Second message")
      }
      item {
        Text(text = "Third message")
      }

    }
    // Jump to bottom button shows up when user scrolls past a threshold.
    // Convert to pixels:
//    val jumpThreshold = with(LocalDensity.current) {
//      JumpToBottomThreshold.toPx()
//    }

    // Show the button if the first visible item is not the first one or if the offset is
    // greater than the threshold.
//    val jumpToBottomButtonEnabled by remember {
//      derivedStateOf {
//        scrollState.firstVisibleItemIndex != 0 ||
//            scrollState.firstVisibleItemScrollOffset > jumpThreshold
//      }
//    }

//    JumpToBottom(
//      // Only show if the scroller is not at the bottom
//      enabled = jumpToBottomButtonEnabled,
//      onClicked = {
//        scope.launch {
//          scrollState.animateScrollToItem(0)
//        }
//      },
//      modifier = Modifier.align(Alignment.BottomCenter)
//    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelNameBar(channelName: String) {
  KodecochatAppBar(
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Channel name
        Text(
          text = channelName,
        )
      }
    },
    actions = {
      // Info icon
      Icon(
        imageVector = Icons.Outlined.Info,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
          .clickable(onClick = { })
          .padding(horizontal = 12.dp, vertical = 16.dp)
          .height(24.dp),
        contentDescription = stringResource(id = R.string.info)
      )
    }
  )
}
