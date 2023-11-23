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

package com.kodeco.chat.data

import com.kodeco.chat.conversation.ConversationUiState
import com.kodeco.chat.conversation.Message
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.data.model.User
import kotlinx.datetime.Instant

private val sampleMessageTexts = listOf(
    Message(
        roomId = "Android Apprentice",
        createdOn = Instant.parse("2023-11-16T05:48:01Z"),
        text = "Hey what do you think of this new Android Apprentice book 📖 from https://Kodeco.com ?",
        userId = "other"
    ),
    Message(
        roomId = "Android Apprentice",
        createdOn = Instant.parse("2023-11-17T05:48:01Z"),
        text = "it's pretty 😍 awesome 💯😎. I learned how to make some cool apps including this chat app! 😄🤩🎉",
        userId = "me"
    ),
    Message(
        roomId = "Android Apprentice",
        text = "Wow!",
        userId = "other"
    ),
)

private val meUser = User(id = "me", firstName = "Fuad", lastName = "Kamal")
private val otherUser = User(id = "other", firstName = "Sally", lastName = "Walden")

val initialMessages = listOf(
    MessageUiModel(message = sampleMessageTexts[2], user = otherUser, id = "2"),
    MessageUiModel(message = sampleMessageTexts[1], user = meUser, id = "1"),
    MessageUiModel(message = sampleMessageTexts[0], user = otherUser, id = "0")
)

//val exampleUiState = ConversationUiState(
//    initialMessages = initialMessages,
//    channelName = "#Android Apprentice",
//)