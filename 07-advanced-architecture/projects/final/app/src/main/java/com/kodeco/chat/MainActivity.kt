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

package com.kodeco.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodeco.chat.DittoHandler.Companion.ditto
import com.kodeco.chat.conversation.ConversationContent
import com.kodeco.chat.conversation.ConversationUiState
import com.kodeco.chat.data.model.MessageUiModel
import com.kodeco.chat.theme.KodecochatTheme
import com.kodeco.chat.viewmodel.MainViewModel
import live.ditto.Ditto
import live.ditto.DittoIdentity
import live.ditto.DittoLogLevel
import live.ditto.DittoLogger
import live.ditto.android.DefaultAndroidDittoDependencies
import live.ditto.transports.DittoSyncPermissions

class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val messagesWithUsers: List<MessageUiModel> by viewModel
        .roomMessagesWithUsersFlow
        .collectAsStateWithLifecycle(initialValue = emptyList())

      val currentUiState =
        ConversationUiState(
          channelName = "Android Apprentice",
          initialMessages = messagesWithUsers.asReversed(),
          viewModel = viewModel
        )

      KodecochatTheme {
        ConversationContent(
          currentUiState,
        )
      }
    }
    checkPermissions()
    setupDitto()
  }

  private fun checkPermissions() {
    val missing = DittoSyncPermissions(this).missingPermissions()
    if (missing.isNotEmpty()) {
      this.requestPermissions(missing, 0)
    }
  }

  private fun setupDitto() {
    val androidDependencies = DefaultAndroidDittoDependencies(applicationContext)
    DittoLogger.minimumLogLevel = DittoLogLevel.DEBUG
    ditto = Ditto(
      androidDependencies,
      DittoIdentity.OnlinePlayground(
        androidDependencies,
        appId = BuildConfig.DITTO_APP_ID,
        token = BuildConfig.DITTO_TOKEN
      )
    )
    ditto.startSync()
  }

}

