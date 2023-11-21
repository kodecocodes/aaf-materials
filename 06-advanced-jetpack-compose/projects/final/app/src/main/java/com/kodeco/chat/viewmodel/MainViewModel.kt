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

package com.kodeco.chat.viewmodel

import android.net.Uri
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.chat.conversation.Message
import com.kodeco.chat.data.DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID
import com.kodeco.chat.data.initialMessages
import com.kodeco.chat.data.model.ChatRoom
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.data.model.User
import com.kodeco.chat.data.model.toIso8601String
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

class MainViewModel : ViewModel() {
  /**
   * hard coding the userID here
   * ideally you would generate this on first app launch, then store it for future use
   * see chapter 8 to see how you would store a value like this in Data Store
   * for example
   * `userID = userPreferencesRepository.fetchInitialPreferences().currentUserId`
   */
  private val userId = UUID.randomUUID().toString()

  private val allMessagesForRoom: MutableStateFlow<List<Message>> by lazy {
    MutableStateFlow(emptyList())
  }

  private val _messages: MutableList<MessageUiModel> = initialMessages.toMutableStateList()

  private val _messagesFlow: MutableStateFlow<List<MessageUiModel>> by lazy {
    MutableStateFlow(emptyList())
  }
  val messages = _messagesFlow.asStateFlow()

  private val emptyChatRoom = ChatRoom(
    id = "public",
    name = "Android Apprentice",
    createdOn = Clock.System.now(),
    messagesCollectionId = DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID,
    isPrivate = false,
    collectionID = "public",
    createdBy = "Kodeco User"
  )

  private val _currentChatRoom = MutableStateFlow(emptyChatRoom)
  val currentRoom = _currentChatRoom.asStateFlow()

  fun setCurrentChatRoom(newChatChatRoom: ChatRoom) {
    _currentChatRoom.value = newChatChatRoom
  }

  fun onCreateNewMessageClick(messageText: String, photoUri: Uri?) {
    val currentMoment: Instant = Clock.System.now()
    val message = Message(
      UUID.randomUUID().toString(),
      currentMoment,
      currentRoom.value.id,
      messageText,
      userId,
      photoUri
    )

    if (message.photoUri == null) {
      viewModelScope.launch(Dispatchers.Default) {
        createMessageForRoom(message, currentRoom.value)
      }
    }
  }

  suspend fun createMessageForRoom(message: Message, chatRoom: ChatRoom) {
    val currentMoment: Instant = Clock.System.now()
    val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
    val dateString = datetimeInUtc.toIso8601String()

    val user = User(userId)
    val messageUIModel = MessageUiModel(message, user)
    _messages.add(messageUIModel)
    _messagesFlow.emit(_messages)

  }



}