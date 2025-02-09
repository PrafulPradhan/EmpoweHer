package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.Answer
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AnswerViewModel(questionId:String) :ViewModel(){
    val response: MutableState<DataState> =mutableStateOf(DataState.Empty)
    val questionId=questionId
    init {
        fetch()
    }
    private fun fetch(){
        val answers= mutableListOf<Answer>()
        response.value=DataState.Loading

        FirebaseDatabase.getInstance().getReference("Questions/${questionId}/answers").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val e=data.getValue(Answer::class.java)
                    if (e!=null) {
                        answers.add(e)
                    }
                }
                response.value=DataState.SuccessAnswer(answers)
                Log.d("slots",response.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                response.value=DataState.Failure(error.message)
            }

        })

    }
}