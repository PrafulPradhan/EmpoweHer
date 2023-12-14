package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuestionViewModel : ViewModel(){
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    init {
        fetch()
    }
    private fun fetch(){
        val questions= mutableListOf<Question>()
        response.value= DataState.Loading

        FirebaseDatabase.getInstance().getReference("Questions").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val e=data.getValue(Question::class.java)
                    if (e!=null) {
                        questions.add(e)
                    }
                }
                Log.d("hello",questions.toString())
                response.value= DataState.SuccessQuestion(questions)
            }

            override fun onCancelled(error: DatabaseError) {
                response.value= DataState.Failure(error.message)
            }

        })

    }
}