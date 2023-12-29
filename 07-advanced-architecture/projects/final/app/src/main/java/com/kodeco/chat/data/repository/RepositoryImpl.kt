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

package com.kodeco.chat.data.repository

import com.kodeco.chat.DittoHandler.Companion.ditto
import com.kodeco.chat.conversation.Message
import com.kodeco.chat.data.DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID
import com.kodeco.chat.data.collectionIdKey
import com.kodeco.chat.data.createdByKey
import com.kodeco.chat.data.createdOnKey
import com.kodeco.chat.data.dbIdKey
import com.kodeco.chat.data.firstNameKey
import com.kodeco.chat.data.isPrivateKey
import com.kodeco.chat.data.lastNameKey
import com.kodeco.chat.data.messagesIdKey
import com.kodeco.chat.data.model.ChatRoom
import com.kodeco.chat.data.model.User
import com.kodeco.chat.data.model.toIso8601String
import com.kodeco.chat.data.nameKey
import com.kodeco.chat.data.publicKey
import com.kodeco.chat.data.publicRoomTitleKey
import com.kodeco.chat.data.publicRoomsCollectionId
import com.kodeco.chat.data.roomIdKey
import com.kodeco.chat.data.textKey
import com.kodeco.chat.data.thumbnailKey
import com.kodeco.chat.data.userIdKey
import com.kodeco.chat.data.usersKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import live.ditto.Ditto
import live.ditto.DittoAttachment
import live.ditto.DittoCollection
import live.ditto.DittoDocument
import live.ditto.DittoLiveQuery
import live.ditto.DittoSortDirection
import live.ditto.DittoSubscription
import java.util.UUID

class RepositoryImpl : Repository {

  /**
   * Provide Singleton instance
   * Better approach is to use Dependency Injection
   */
  companion object {
    @Volatile
    private var instance: RepositoryImpl? = null

    fun getInstance() =
      instance ?: synchronized(this) {
        instance ?: RepositoryImpl().also { instance = it }
      }
  }

  private val allMessagesForRoom: MutableStateFlow<List<Message>> by lazy {
    MutableStateFlow(emptyList())
  }

  private val allPublicRooms: MutableStateFlow<List<ChatRoom>> by lazy {
    MutableStateFlow(emptyList())
  }


  private val allUsers: MutableStateFlow<List<User>> by lazy {
    MutableStateFlow(emptyList())
  }

  /**
   * Messages
   */
  private var messagesDocs = listOf<DittoDocument>()
  private lateinit var messagesCollection: DittoCollection
  private lateinit var messagesLiveQuery: DittoLiveQuery
  private lateinit var messagesSubscription: DittoSubscription

  /**
   * Rooms
   */
  private lateinit var publicRoomsCollection: DittoCollection
  private lateinit var publicRoomsSubscription: DittoSubscription
  private lateinit var publicRoomsLiveQuery: DittoLiveQuery

  /**
   * Users
   */
  private var usersDocs = listOf<DittoDocument>()
  private lateinit var usersCollection: DittoCollection
  private lateinit var usersLiveQuery: DittoLiveQuery
  private lateinit var usersSubscription: DittoSubscription

  init {
    initDatabase(this::postInitActions)
  }


  /**
   * Populates Ditto with sample messages if public chat room is empty
   */
  private fun initDatabase(postInitAction: suspend () -> Unit) {
    GlobalScope.launch {
      postInitAction.invoke()
    }
  }

  override fun getAllMessagesForRoom(chatRoom: ChatRoom): Flow<List<Message>> {
    getAllMessagesForRoomFromDitto(chatRoom)
    return allMessagesForRoom
  }

  override fun getAllUsers(): Flow<List<User>> = allUsers

  override fun getAllPublicRooms(): Flow<List<ChatRoom>> = allPublicRooms

  /**
   * You can implement this after you learn how to use Data Store in
   * Chapter 8, "Data Store"
   */
  override suspend fun saveCurrentUser(userId: String, firstName: String, lastName: String) {
    val user = User(userId, firstName, lastName)
    addUser(user)
  }

  /**
   * Typically you would want to store and retrieve user ID
   * from Data Store
   * Since we haven't covered Data Store at this point, it's generated and passed
   * from the ViewModel, and is lost between app launches
   */
  override suspend fun createMessageForRoom(
    userId: String,
    message: Message,
    chatRoom: ChatRoom,
    attachment: DittoAttachment?
  ) {
    val currentMoment: Instant = Clock.System.now()
    val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
    val dateString = datetimeInUtc.toIso8601String()
    val collection = ditto.store.collection(chatRoom.messagesCollectionId)
    val doc = mapOf(
      createdOnKey to dateString,
      roomIdKey to message.roomId,
      textKey to message.text,
      userIdKey to userId,
      thumbnailKey to attachment
    )

    collection.upsert(doc)
  }

  override suspend fun addUser(user: User) {
    ditto.store.collection(usersKey)
      .upsert(
        mapOf(
          dbIdKey to user.id,
          firstNameKey to user.firstName,
          lastNameKey to user.lastName
        )
      )
  }

  override suspend fun createRoom(name: String, isPrivate: Boolean, userId: String) {
    val roomId = UUID.randomUUID().toString()
    val messagesId = UUID.randomUUID().toString()
    var collectionId: String = publicRoomsCollectionId
    val currentMoment: Instant = Clock.System.now()
    val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
    val dateString = datetimeInUtc.toIso8601String()
    if (isPrivate) {
      collectionId = UUID.randomUUID().toString()
    }

    val chatRoom = ChatRoom(
      id = roomId,
      name = name,
      messagesCollectionId = messagesId,
      isPrivate = isPrivate,
      collectionID = collectionId,
      createdBy = userId,
    )

    val doc = mapOf(
      dbIdKey to chatRoom.id,
      nameKey to chatRoom.name,
      messagesIdKey to chatRoom.messagesCollectionId,
      isPrivateKey to chatRoom.isPrivate,
      collectionIdKey to chatRoom.collectionID,
      createdByKey to chatRoom.createdBy,
      createdOnKey to dateString
    )


    addSubscriptionForRoom(chatRoom)

    ditto.let {
      ditto.store.collection(collectionId).upsert(doc)
    }
  }

  private fun addSubscriptionForRoom(chatRoom: ChatRoom) {
      val messageSubscription = ditto.store[chatRoom.messagesCollectionId].findAll().subscribe()
  }

  // This function without room param is for qrCode join private room, where there isn't yet a room
  private fun addPrivateRoomSubscriptions(
    roomId: String,
    collectionId: String,
    messagesId: String
  ) {

    ditto.let { ditto: Ditto ->
      val privateRoomCollection = ditto.store.collection(collectionId)
      val roomSubscription = privateRoomCollection.findAll().subscribe()
      val privateRoomLiveQuery: DittoLiveQuery = privateRoomCollection
        .findAll()
        .sort(createdOnKey, DittoSortDirection.Ascending)
        .observeLocal { docs, _ ->
          val roomDocs = docs
          val privateChatRooms = docs.map { ChatRoom(it) }
        }
      val messagesSubscription = ditto.store.collection(messagesId).findAll().subscribe()
    }
  }

  private fun postInitActions() {
    getAllUsersFromDitto()
    getPublicRoomsFromDitto()
  }

  private fun getAllMessagesForRoomFromDitto(chatRoom: ChatRoom) {
    ditto.let { ditto: Ditto ->
      messagesCollection = ditto.store.collection(chatRoom.messagesCollectionId)
      messagesSubscription = messagesCollection.findAll().subscribe()
      messagesLiveQuery = messagesCollection
        .findAll()
        .sort(createdOnKey, DittoSortDirection.Ascending)
        .observeLocal { docs, _ ->
          this.messagesDocs = docs
          allMessagesForRoom.value = docs.map { Message(it) }
        }
    }

  }

  private fun getPublicRoomsFromDitto() {
    ditto.let { ditto: Ditto ->
      publicRoomsCollection = ditto.store.collection(publicRoomsCollectionId)
      publicRoomsSubscription = publicRoomsCollection.findAll().subscribe()
      publicRoomsLiveQuery = publicRoomsCollection
        .findAll()
        .observeLocal { docs, _ ->
          allPublicRooms.value = docs.map { ChatRoom(it) }
        }

    }
  }

  override suspend fun publicRoomForId(roomId: String): ChatRoom {
    val document = ditto.store.collection(publicRoomsCollectionId).findById(roomId).exec()
    document?.let {
      return ChatRoom(document)
    }
    return ChatRoom(
      id = publicKey,
      name = publicRoomTitleKey,
      createdOn = Clock.System.now(),
      messagesCollectionId = DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID,
      isPrivate = false,
      collectionID = publicKey,
      createdBy = "Ditto System"
    )
  }

  override fun getDittoSdkVersion(): String {
    return ditto.sdkVersion
  }

  private fun getAllUsersFromDitto() {
    ditto.let { ditto: Ditto ->
      usersCollection = ditto.store.collection(usersKey)
      usersSubscription = usersCollection.findAll().subscribe()
      usersLiveQuery = usersCollection.findAll().observeLocal { docs, _ ->
        this.usersDocs = docs
        allUsers.value = docs.map { User(it) }
      }
    }
  }
}