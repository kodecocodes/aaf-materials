package com.kodeco.chat.conversation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kodeco.chat.R

@Composable
fun ConversationContent() {
  Column {
    val context = LocalContext.current
    var chatInputText by remember { mutableStateOf("") }
    var chatOutputText by remember { mutableStateOf(context.getString(R.string.chat_display_default)) }
    Text(text = chatOutputText)

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