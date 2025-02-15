package com.example.empoweher.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.Answer
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Slot
import com.example.empoweher.model.Time
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TimingViewModel(userId:String) : ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Loading)
    val userId=userId
    init {
        fetch()
    }
    private fun fetch() {
        val slots= mutableListOf<Slot>()
        FirebaseDatabase.getInstance().getReference("Users/${userId}/Schedule").addListenerForSingleValueEvent(object:
            ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    for(data2 in data.children){
                        val e2 = data2.getValue(Slot::class.java)
                            if (e2!=null) {
                                slots.add(e2)
                            }
                    }
                }

                Log.d("slots",slots.toString())

                val orderedSlots=slots.sortedBy { slots-> slots.index?.toInt() }

                response.value= DataState.SuccessSlots(data=orderedSlots.toMutableList())
                Log.d("slots",response.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("slots","inside cancelled")
                response.value= DataState.Failure(error.message)
                Log.d("slots",response.value.toString())
            }

        })

    }
}