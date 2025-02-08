package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Daylist
import com.example.empoweher.model.Slot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SlotViewModel : ViewModel(){
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    init {
        fetch()
    }
    private fun fetch(){
        val _selectedSlots = MutableStateFlow(List(0) { "" })  // 12 buttons
        val selectedSlots: StateFlow<List<String>> = _selectedSlots.asStateFlow()
        val slotMorning:MutableList<MutableList<Slot>> = mutableListOf()
        val slotEvening:MutableList<MutableList<Slot>> = mutableListOf()
        val morning= listOf("9:00","10:00","11:00","12:00","13:00","14:00")
        val evening= listOf("16:00","17:00","18:00","19:00","20:00","21:00")
        val statusList = mutableListOf<String>()
        response.value= DataState.Loading
        val currentFirebaseUser= FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("Users/${currentFirebaseUser}/Schedule").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val slotEve = mutableListOf<Slot>()
                    val slotMorn = mutableListOf<Slot>()
                    for(data2 in data.children){
                        val e2 = data2.getValue(Slot::class.java)
                        if (e2!=null) {
                            Log.d("slots",e2.toString())
                            if(e2.start in morning) {
                                slotMorn.add(e2)
                                statusList.add(e2.status!!)
                                _selectedSlots.value = _selectedSlots.value.toMutableList().apply {
                                    this.add(e2.status!!)
                                }
                            }
                            else if(e2.start in evening) {
                                slotEve.add(e2)
                                statusList.add(e2.status!!)
                                _selectedSlots.value = _selectedSlots.value.toMutableList().apply {
                                    this.add(e2.status!!)
                                }
                            }
                        }
                    }
                    slotMorning.add(slotMorn)
                    slotEvening.add(slotEve)
                }
                response.value= DataState.SuccessSlot(data=slotMorning, data2 = slotEvening, data3 = statusList)
            }

            override fun onCancelled(error: DatabaseError) {
                response.value= DataState.Failure(error.message)
            }

        })

    }
}