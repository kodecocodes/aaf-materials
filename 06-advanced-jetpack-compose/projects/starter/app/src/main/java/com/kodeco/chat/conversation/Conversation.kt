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

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.kodeco.chat.R
import com.kodeco.chat.components.KodecochatAppBar
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.utilities.isoToTimeAgo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationContent(uiState: ConversationUiState) {

  val scrollState = rememberLazyListState()
  val scope = rememberCoroutineScope()
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
          messages = uiState.messages,
          modifier = Modifier.weight(1f)
//          scrollState = scrollState
        )
        UserInput(onMessageSent = {})
      }
      // Channel name bar floats above the messages
      ChannelNameBar(channelName = "Android Apprentice")
    }
  }


}

@Composable
fun Messages(
  messages: List<MessageUiModel>,
//  scrollState: LazyListState,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    LazyColumn(
//      state = scrollState,
      // Add content padding so that the content can be scrolled (y-axis)
      // below the status bar + app bar
      contentPadding =
      WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
      modifier = Modifier
        .fillMaxSize()
    ) {
      itemsIndexed(
        items = messages,
        key = { _, message -> message.id }
      ) { index, content ->
        val prevAuthor = messages.getOrNull(index - 1)?.message?.userId
        val nextAuthor = messages.getOrNull(index + 1)?.message?.userId
        val userId = messages.getOrNull(index)?.message?.userId
        val isFirstMessageByAuthor = prevAuthor != content.message.userId
        val isLastMessageByAuthor = nextAuthor != content.message.userId
        MessageUi(
          onAuthorClick = { },
          msg = content,
          authorId = "me",
          userId = userId ?: "",
          isFirstMessageByAuthor = isFirstMessageByAuthor,
          isLastMessageByAuthor = isLastMessageByAuthor,
        )
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

@Composable
fun MessageUi(
  onAuthorClick: (String) -> Unit,
  msg: MessageUiModel,
  authorId: String,
  userId: String,
  isFirstMessageByAuthor: Boolean,
  isLastMessageByAuthor: Boolean,
) {
  val isUserMe = userId == "me" // hard coded for now, next chapter will be = authorId == userId
  val borderColor = if (isUserMe) {
    MaterialTheme.colorScheme.primary
  } else {
    MaterialTheme.colorScheme.tertiary
  }

  val authorImageId: Int =
    if (isUserMe) R.drawable.profile_photo_android_developer else R.drawable.someone_else
  val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
  Row(modifier = spaceBetweenAuthors) {
    if (isLastMessageByAuthor) {
      // Avatar
      Image(
        modifier = Modifier
          .clickable(onClick = { onAuthorClick(msg.message.userId) })
          .padding(horizontal = 16.dp)
          .size(42.dp)
          .border(1.5.dp, borderColor, CircleShape)
          .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
          .clip(CircleShape)
          .align(Alignment.Top),
        painter = painterResource(id = authorImageId),
        contentScale = ContentScale.Crop,
        contentDescription = null
      )
    } else {
      // Space under avatar
      Spacer(modifier = Modifier.width(74.dp))
    }
    AuthorAndTextMessage(
      msg = msg,
      isUserMe = isUserMe,
      isFirstMessageByAuthor = isFirstMessageByAuthor,
      isLastMessageByAuthor = isLastMessageByAuthor,
      authorClicked = onAuthorClick,
      modifier = Modifier
        .padding(end = 16.dp)
        .weight(1f)
    )
  }
}

@Composable
fun AuthorAndTextMessage(
  msg: MessageUiModel,
  isUserMe: Boolean,
  isFirstMessageByAuthor: Boolean,
  isLastMessageByAuthor: Boolean,
  authorClicked: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    if (isLastMessageByAuthor) {
      AuthorNameTimestamp(msg, isUserMe)
    }
    ChatItemBubble(
      msg.message,
      isUserMe,
      authorClicked = authorClicked
    )
    if (isFirstMessageByAuthor) {
      // Last bubble before next author
      Spacer(modifier = Modifier.height(8.dp))
    } else {
      // Between bubbles
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}

@Composable
private fun AuthorNameTimestamp(msg: MessageUiModel, isUserMe: Boolean = false) {
  var userFullName: String = msg.user.fullName
  if (isUserMe) {
    userFullName = "me"
  }

  // Combine author and timestamp for author.
  Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
    Text(
      text = userFullName,
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier
        .alignBy(LastBaseline)
        .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = msg.message.createdOn.toString().isoToTimeAgo(),
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.alignBy(LastBaseline),
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}


@Composable
fun ChatItemBubble(
  message: Message,
  isUserMe: Boolean,
  authorClicked: (String) -> Unit
) {
  val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
  val pressedState = remember { mutableStateOf(false) }
  val backgroundBubbleColor = if (isUserMe) {
    MaterialTheme.colorScheme.primary
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }

  Column {
    Surface(
      color = backgroundBubbleColor,
      shape = ChatBubbleShape
    ) {
      if (message.text.isNotEmpty()) {
        ClickableMessage(
          message = message,
          isUserMe = isUserMe,
          authorClicked = authorClicked
        )
      }
    }
  }
}

@Composable
fun ClickableMessage(
  message: Message,
  isUserMe: Boolean,
  authorClicked: (String) -> Unit
) {
  val uriHandler = LocalUriHandler.current

  val styledMessage = messageFormatter(
    text = message.text,
    primary = isUserMe
  )

  ClickableText(
    text = styledMessage,
    style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
    modifier = Modifier.padding(16.dp),
    onClick = {
      styledMessage
        .getStringAnnotations(start = it, end = it)
        .firstOrNull()
        ?.let { annotation ->
          when (annotation.tag) {
            SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
            SymbolAnnotationType.PERSON.name -> authorClicked(annotation.item)
            else -> Unit
          }
        }
    }
  )
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
