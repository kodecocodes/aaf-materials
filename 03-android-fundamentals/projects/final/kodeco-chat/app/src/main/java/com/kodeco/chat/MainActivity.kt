package com.kodeco.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                var chatInputText by remember { mutableStateOf("Type your text here") }
                var chatOutputText by remember { mutableStateOf("Press Send to update this text")}
                Text(text = chatOutputText)

                OutlinedTextField(
                    value = chatInputText,
                    onValueChange = {
                        chatInputText = it
                                    },
                    label = { Text("Label") }
                )

                Button(onClick = { chatOutputText = chatInputText}) {
                    Text(text = "Send")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

}

