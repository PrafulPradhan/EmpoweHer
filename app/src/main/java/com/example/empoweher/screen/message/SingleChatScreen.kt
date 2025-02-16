package com.example.empoweher.screen.message

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.message.data.ChatUser
import com.example.empoweher.screen.message.data.Message
import com.example.empoweher.screen.message.utils.CommonDivider
import com.example.empoweher.screen.message.utils.CommonImage

@Composable
fun SingleChatScreen( vm: ChatViewModel, chatId: String,navigateToNextScreen: (route: String) -> Unit) {
    var reply by rememberSaveable {
        mutableStateOf("")
    }
    val onSendReply = {
        vm.onSendReply(chatId, reply)
        reply = ""
    }
    val myUser = vm.userData.value
    val currentChat = vm.chats.value.find {it.chatId == chatId }
    Log.d("chatId", currentChat.toString())
    var chatUser:ChatUser=ChatUser("24Si2cNeD8Uq7vIbGCTDUSAHNOg1","Aman Sande","https://firebasestorage.googleapis.com/v0/b/empowerher-39d60.appspot.com/o/24Si2cNeD8Uq7vIbGCTDUSAHNOg1%2FProfile%20Picture?alt=media&token=96ffc0e4-90c4-44a3-b2df-203c47694319")
    if (currentChat!=null) {
        chatUser =
            if (myUser?.userID == currentChat.user1.userId) currentChat.user2 else currentChat.user1
    }
    LaunchedEffect(key1 = Unit) {
        vm.populateMessages(chatId)
    }
    BackHandler {
        vm.depopulateMessage()
    }
        Column {
            if (myUser != null) {
                ChatHeader(
                    name = chatUser.name ?: "",
                    imageUrl = chatUser.imageUrl ?: "",
                    navigateToNextScreen = navigateToNextScreen,
                    userId = myUser.userID!!
                ) {
                    vm.depopulateMessage()
                }
            }
            MessageBox(
                modifier = Modifier.weight(1f),
                chatMessage = vm.chatMessages.value,
                currentUserId = myUser?.userID ?: ""
            )
            ReplyBox(reply = reply, onReplyChange = {
                reply = it
            }, onSendReply)
        }
}

@Composable
fun MessageBox(modifier: Modifier, chatMessage: List<Message>, currentUserId: String) {
    LazyColumn(modifier = modifier) {
        items(chatMessage) { msg ->
            val alignment = if (msg.sendBy == currentUserId) Alignment.End else Alignment.Start
            val color = if (msg.sendBy == currentUserId) Color(0xFF68C400) else Color(0xFFC0C0C0)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = alignment
            ) {
                Text(
                    text = msg.message ?: "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                        .padding(12.dp),
                    color= Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(value = reply, onValueChange = onReplyChange, maxLines = 3)
            Button(onClick = {
                onSendReply()
            }) {
                Text(text = "Send")
            }
        }
    }
}

@Composable
fun ChatHeader(name: String, imageUrl: String,navigateToNextScreen: (route: String) -> Unit,userId:String,onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.ArrowBack, contentDescription = null, modifier = Modifier
            .clickable {
                onBackClicked.invoke()
                navigateToNextScreen(Screen.Profile.route + "/" + userId)
            }
            .padding(8.dp))
        CommonImage(
            data = imageUrl, modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}