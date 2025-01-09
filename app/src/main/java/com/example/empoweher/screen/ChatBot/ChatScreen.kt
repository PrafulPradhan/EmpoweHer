package com.example.empoweher.screen.ChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navigateToNextScreen: (route: String) -> Unit) {
    val chatbot = remember { ChatbotLogic() }
    val messages = remember { mutableStateListOf("Bot: ${chatbot.getMessage()}") }
    var userInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1)) // Light gray background
    ) {
        // Header
        TopAppBar(
            title = { Text("Chatty") },
            modifier = Modifier.background(Color(0xFF6C63FF))
        )
        ChatbotUI(navigateToNextScreen)
        // Chat Messages
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(Color.Transparent),
                placeholder = { Text("Type a message") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        val userMessage = "You: $userInput"
                        messages.add(userMessage)

                        val botResponse = "Bot: ${chatbot.handleCommand(userInput, navigateToNextScreen)}"
                        messages.add(botResponse)

                        userInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF6C63FF)),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}
