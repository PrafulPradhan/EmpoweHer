package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.Answer
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileViewModel() :ViewModel(){
    val response: MutableState<DataState> =mutableStateOf(DataState.Empty)
    init {
        fetch()
    }
    private fun fetch(){
        val users= mutableListOf<User>()
        response.value=DataState.Loading

        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val e=data.getValue(User::class.java)
                    if (e!=null) {
                        Log.d("users",e.toString())
                        users.add(e)
                    }
                }
                response.value=DataState.SuccessUser(users)
            }

            override fun onCancelled(error: DatabaseError) {
                response.value=DataState.Failure(error.message)
            }

        })

    }
}