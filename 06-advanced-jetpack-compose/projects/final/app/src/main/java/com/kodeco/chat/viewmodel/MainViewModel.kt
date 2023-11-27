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
import com.kodeco.chat.data.model.ChatRoom
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import live.ditto.DittoAttachmentToken
import live.dittolive.chat.data.repository.RepositoryImpl
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
  var currentUserId = MutableStateFlow(userId)

  private var firstName: String = ""
  private var lastName: String = ""

  private val repository = RepositoryImpl.getInstance()

//  private val allMessagesForRoom: MutableStateFlow<List<Message>> by lazy {
//    MutableStateFlow(emptyList())
//  }

//  private val _messages: MutableList<MessageUiModel>

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

  // messages for a particular chat room
  val roomMessagesWithUsersFlow: Flow<List<MessageUiModel>> = combine(
    repository.getAllUsers(),
    repository.getAllMessagesForRoom(currentRoom.value)
  ) { users: List<User>, messages:List<Message> ->

    messages.map {
      MessageUiModel.invoke(
        message = it,
        users = users
      )
    }
  }

  init {
      // user initialziation - we use the device name for the user's name
      val firstName = "My"
      val lastName = android.os.Build.MODEL
      updateUserInfo(firstName, lastName)
  }

  fun updateUserInfo(firstName: String = this.firstName, lastName: String = this.lastName) {
    viewModelScope.launch {
      repository.saveCurrentUser(userId, firstName, lastName)
    }
  }

  fun onCreateNewMessageClick(messageText: String, photoUri: Uri?, attachmentToken: DittoAttachmentToken?) {
    val currentMoment: Instant = Clock.System.now()
    val message = Message(
      UUID.randomUUID().toString(),
      currentMoment,
      currentRoom.value.id,
      messageText,
      userId,
      attachmentToken,
      photoUri
    )

    if (message.photoUri == null) {
      viewModelScope.launch(Dispatchers.Default) {
        repository.createMessageForRoom(userId, message, currentRoom.value, null)
      }
    }
  }


}