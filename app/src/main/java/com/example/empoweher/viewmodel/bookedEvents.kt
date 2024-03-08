package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class bookedEvents() :ViewModel(){
    val response: MutableState<DataState> =mutableStateOf(DataState.Empty)
    var list: MutableList<String> = mutableListOf()

    init {
        FirebaseDatabase.getInstance().getReference("Users/24Si2cNeD8Uq7vIbGCTDUSAHNOg1/bookedEvents").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val e = data.getValue(String::class.java)
                    Log.d("jwsh",e.toString())
                    list.add(e!!)
                }

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        fetch()
    }

    fun fetch(){
        val events= mutableListOf<Event>()
        response.value=DataState.Loading

        FirebaseDatabase.getInstance().getReference("Event").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val e = data.getValue(Event::class.java)
                        if (e != null && list.contains(e.eventId) ) {
                            events.add(e)
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