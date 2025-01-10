package com.example.empoweher.screen.ChatBot

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.composables.getInfoUser
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatBubble(message: String) {
    val user = FirebaseAuth.getInstance().currentUser
    var userId = ""
    if (user != null) {
        userId = user.uid
        // Use the userId
    }

    Log.d("Firebase", userId)
    val isUser = message.startsWith("You:")
    val displayMessage = message.removePrefix("You: ").removePrefix("Bot: ")
    val dp = getInfoUser(thing = "Dp", userId = userId)
    val image = rememberAsyncImagePainter(dp)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if(!isUser){
            Image(
                painter = painterResource(R.drawable.chatbot),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(35.dp)
                    .padding(top =10.dp, end = 5.dp),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = if (isUser) Color(0xFF6C63FF) else Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = displayMessage,
                color = if (isUser) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if(isUser){
            Image(
                painter = image,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 10.dp, start = 5.dp)
                    .clip(CircleShape),

                contentScale = ContentScale.Fit
            )
        }
    }
}
