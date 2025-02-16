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

class mainviewmodel :ViewModel(){
    val response: MutableState<DataState> =mutableStateOf(DataState.Empty)
    init {
        fetch("all")
    }
    fun fetch(tag:String){
        val events= mutableListOf<Event>()
        response.value=DataState.Loading

        FirebaseDatabase.getInstance().getReference("Event").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (tag=="all") {
                    for (data in snapshot.children) {
                        val e = data.getValue(Event::class.java)
                        if (e != null) {
                            events.add(e)
                        }
                    }
                }
                else{
                    for (data in snapshot.children){
                        val e=data.getValue(Event::class.java)
                        if (e!=null && e.tag==tag) {
                            events.add(e)
                        }
                    }
                }
                Log.d("hello",events.toString())
                response.value=DataState.Success(events)
            }

            override fun onCancelled(error: DatabaseError) {
                response.value=DataState.Failure(error.message)
            }

        })

    }
}