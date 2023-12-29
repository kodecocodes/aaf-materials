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

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.toMutableStateList
import com.kodeco.chat.R
import com.kodeco.chat.data.createdOnKey
import com.kodeco.chat.data.dbIdKey
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.data.model.toInstant
import com.kodeco.chat.data.roomIdKey
import com.kodeco.chat.data.textKey
import com.kodeco.chat.data.thumbnailKey
import com.kodeco.chat.data.userIdKey
import com.kodeco.chat.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import live.ditto.DittoAttachmentToken
import live.ditto.DittoDocument
import java.util.UUID

class ConversationUiState(
  val channelName: String,
  initialMessages: List<MessageUiModel>,
  val viewModel: MainViewModel
) {
  private val _messages: MutableList<MessageUiModel> = initialMessages.toMutableStateList()

  val messages: List<MessageUiModel> = _messages

  //author ID is set to the user ID - it's used to tell if the message is sent from this user (self) when rendering the UI
  val authorId: MutableStateFlow<String> = viewModel.currentUserId

  fun addMessage(msg: String, photoUri: Uri?) {
    viewModel.onCreateNewMessageClick(msg, photoUri, null)
  }
}

@Immutable
data class Message(
  val _id: String = UUID.randomUUID().toString(),
  val createdOn: Instant? = Clock.System.now(),
  val roomId: String = "public", // "public" is the roomID for the default public chat room
  val text: String = "test",
  val userId: String = UUID.randomUUID().toString(),
  val attachmentToken: DittoAttachmentToken?,
  val photoUri: Uri? = null,
  val authorImage: Int = if (userId == "me") R.drawable.profile_photo_android_developer else R.drawable.someone_else
) {
  constructor(document: DittoDocument) : this(
    document[dbIdKey].stringValue,
    document[createdOnKey].stringValue.toInstant(),
    document[roomIdKey].stringValue,
    document[textKey].stringValue,
    document[userIdKey].stringValue,
    document[thumbnailKey].attachmentToken
  )
}
