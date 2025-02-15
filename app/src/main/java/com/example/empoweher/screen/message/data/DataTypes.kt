package com.example.empoweher.screen.message.data

import java.sql.Timestamp

data class UserData(
    var userID:String?="",
    var name:String?="",
    var Dp:String?="",
){
    fun toMap()=mapOf(
        "userID" to userID,
        "name" to name,
        "Dp" to Dp,
    )
}

data class ChatData(
    val chatId:String?="",
    val user1:ChatUser=ChatUser(),
    val user2:ChatUser= ChatUser()
)
data class ChatUser(
    val userId:String?="",
    val name:String?="",
    val imageUrl: String?="",
)
data class Message(
    val sendBy:String?="",
    val message: String?="",
    val timestamp: String?=""
)
