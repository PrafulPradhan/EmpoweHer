package com.example.empoweher.screen.message

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.empoweher.screen.message.data.CHATS
import com.example.empoweher.screen.message.data.ChatData
import com.example.empoweher.screen.message.data.ChatUser
import com.example.empoweher.screen.message.data.Event
import com.example.empoweher.screen.message.data.Message
import com.example.empoweher.screen.message.data.USER_NODE
import com.example.empoweher.screen.message.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val database: FirebaseDatabase,
) : ViewModel() {
    var inProcess = mutableStateOf(false)
    var inProcessChats = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(value = null)
    var signIn = mutableStateOf(false)
    var signUp = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    var chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    val inProcessChatMessage = mutableStateOf(false)
    val inProgressStatus = mutableStateOf(false)

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun populateMessages(chatId: String) {
        inProcessChatMessage.value = true
        database.getReference(CHATS).child(chatId).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val msg = data.getValue(Message::class.java)
                        if (msg !in chatMessages.value) {
                            chatMessages.value += msg!!
                        }
                        chatMessages.value = chatMessages.value.sortedBy { it.timestamp }
                        inProcessChatMessage.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun depopulateMessage() {
        chatMessages.value = listOf()
    }

    fun populateChats() {
        inProcessChats.value = true
        database.getReference().child(CHATS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val user1Id = data.child("user1").child("userId").getValue(String::class.java)
                    val user2Id = data.child("user2").child("userId").getValue(String::class.java)
                    if (user1Id == userData.value?.userID || user2Id == userData.value?.userID) {
                        val chat = data.getValue(ChatData::class.java)!!
                        if (chat !in chats.value) {
                            chats.value += chat
                        }
                        Log.d("ChatsValue", chats.value.toString())
                        inProcessChats.value = false
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        inProcessChats.value = false
    }

    fun onSendReply(chatId: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val msg = Message(userData.value?.userID, message, time)
        val id = database.getReference().push().key!!
        database.getReference().child(CHATS).child(chatId).child("message").child(id).setValue(msg)
    }

    private fun getUserData(uid: String) {
        inProcess.value = true
        database.getReference(USER_NODE).child(uid).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserData::class.java)
                    userData.value = user
                    inProcess.value = false
                    populateChats()
                }

                override fun onCancelled(error: DatabaseError) {
                    val exception = error.toException()
                    if (error != null) {
                        handleException(exception, "Cannot Retrieve User")
                    }
                }

            }
        )
    }

    fun handleException(exception: Exception? = null, customMessage: String? = "") {
        Log.d("ChatApp", "ChatApp Exception: ", exception)
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMessage else customMessage
        Log.d("Custom message", "Exception : $customMessage")
        eventMutableState.value = Event(message)
        inProcess.value = false
    }

    suspend fun onAddChat(userId: String): String = suspendCancellableCoroutine { continuation ->
        var id = database.getReference().push().key!!
        database.getReference().child(CHATS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val chatData = data.getValue(ChatData::class.java)
                        if (chatData != null && (
                                    (chatData.user1.userId == userData.value?.userID && chatData.user2.userId == userId) ||
                                            (chatData.user1.userId == userId && chatData.user2.userId == userData.value?.userID)
                                    )
                        ) {
                            id=chatData.chatId!!
                            continuation.resume(id)
                            return
                        }
                    }

                    database.getReference().child(USER_NODE).child(userId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val chatPartner = snapshot.getValue(UserData::class.java)

                                if (chatPartner != null) {
                                    val chat = ChatData(
                                        chatId = id,
                                        ChatUser(userData.value?.userID, userData.value?.name, userData.value?.Dp),
                                        ChatUser(chatPartner.userID, chatPartner.name, chatPartner.Dp)
                                    )

                                    database.getReference().child(CHATS).child(id).setValue(chat)
                                        .addOnSuccessListener {
                                            continuation.resume(id)
                                        }
                                        .addOnFailureListener { e ->
                                            continuation.resumeWithException(e)
                                        }
                                } else {
                                    continuation.resumeWithException(Exception("User not found"))
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                continuation.resumeWithException(Exception(error.message))
                            }
                        })
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(Exception(error.message))
                }
            })
    }
}

