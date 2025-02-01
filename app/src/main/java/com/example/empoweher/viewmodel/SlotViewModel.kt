package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Slot
import com.example.empoweher.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SlotViewModel : ViewModel(){
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    init {
        fetch()
    }
    private fun fetch(){
        val slots= mutableListOf<Slot>()
        response.value= DataState.Loading
        val currentFirebaseUser= FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users/${currentFirebaseUser}/Schedule/0").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val e=data.getValue(Slot::class.java)
                    if (e!=null) {
                        Log.d("slots",e.toString())
                        slots.add(e)
                    }
                }
                response.value= DataState.SuccessSlot(slots)
            }

            override fun onCancelled(error: DatabaseError) {
                response.value= DataState.Failure(error.message)
            }

        })

    }
}