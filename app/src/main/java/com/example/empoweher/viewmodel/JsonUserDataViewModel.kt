package com.example.empoweher.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.empoweher.composables.currentFirebaseUser
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.JsonUser
import com.example.empoweher.model.JsonUserEvent
import com.example.empoweher.model.eventUserData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val categoryMap = mutableMapOf(
    "Education" to 0,
    "Safety" to 1,
    "Empowerment" to 2,
    "Daily Guidance" to 3,
    "Arts" to 4,
    "Technical" to 5,
    "Social Affairs" to 6,
    "Child Problems" to 7,
    "Astrology" to 8,
    "Health" to 9,
    "Spiritual" to 10,
    "History" to 11,
    "Career Guidance" to 12,
    "Sports" to 13,
    "Politics" to 14,
    "Exploratory" to 15,
    "Entertainment" to 16,
    "Real Estate" to 17,
    "Business" to 18
)
class JsonUserDataViewModel: ViewModel() {
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    fun fetch(userId: String, onComplete: (List<JsonUserEvent>) -> Unit) {
        val jsonUserEvent = mutableListOf<JsonUserEvent>()
        response.value = DataState.Loading

        FirebaseDatabase.getInstance().getReference("Users").child("${userId}/bookedEvents")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pendingFetches = snapshot.children.count()
                    var completedFetches = 0

                    for (data in snapshot.children) {
                        val e = data.getValue(eventUserData::class.java)
                        FirebaseDatabase.getInstance().getReference("Event")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (eventData in snapshot.children) {
                                        val e2 = eventData.getValue(Event::class.java)
                                        if (e != null && e2 != null && e.eventId == e2.eventId) {
                                            val rating = (e2.totalRating!!.toDouble() / e2.attendees!!.toInt())
                                            val jsonUser = JsonUserEvent(e2.eventId, e2.eventCost!!.toInt(), rating, categoryMap[e2.tag])
                                            jsonUserEvent.add(jsonUser)
                                        }
                                    }
                                    completedFetches++
                                    if (completedFetches == pendingFetches) {
                                        response.value = DataState.SuccessJsonUser(jsonUserEvent)
                                        onComplete(jsonUserEvent)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    response.value = DataState.Failure(error.message)
                                }
                            })
                    }

                    // Handle case where no data is available
                    if (pendingFetches == 0) {
                        response.value = DataState.SuccessJsonUser(jsonUserEvent)
                        onComplete(jsonUserEvent)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }
            })
    }
}