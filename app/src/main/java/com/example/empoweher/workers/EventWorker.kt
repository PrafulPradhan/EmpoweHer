package com.example.empoweher.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.empoweher.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class EventWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        var isStopped = false
    }

    init {
        Companion.isStopped = false
    }

    override fun doWork(): Result {
        var inputData: Data = inputData
        var eventId=inputData.getString("Events")
        val reference = FirebaseDatabase.getInstance().getReference("Event/${eventId}")
        fetchEventData(reference) { endDate, status, timing ->
            if (endDate != null && timing != null && status != null) {
                val currentMillis = System.currentTimeMillis()
                val eventMillis = convertToMillis(endDate, timing)
                Log.d("EventWorker", "Event ID: $eventId, End Date: $endDate, Time: $timing, Status: $status")

                if (currentMillis >= eventMillis && status == "ongoing") {
                    updateEventStatus(reference, "completed")
                    WorkManager.getInstance(context).cancelAllWork()
                }
            }
        }
        return Result.success()
    }
    private fun convertToMillis(dateStr: String, timeStr: String): Long {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val date = dateFormat.parse("$dateStr $timeStr")
            date?.time ?: 0L
        } catch (e: ParseException) {
            Log.e("EventWorker", "Date Parsing Error", e)
            0L
        }
    }

    private fun fetchEventData(reference: DatabaseReference, callback: (String?, String?, String?) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val endDate = snapshot.child("endDate").getValue(String::class.java)
                val status = snapshot.child("status").getValue(String::class.java)
                val timing = snapshot.child("timing").getValue(String::class.java)

                callback(endDate, status, timing)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("EventWorker", "Error fetching data", error.toException())
                callback(null, null, null)
            }
        })
    }

    private fun updateEventStatus(reference: DatabaseReference, newStatus: String) {
        reference.child("status").setValue(newStatus)
            .addOnSuccessListener { Log.d("EventWorker", "Event status updated to: $newStatus") }
            .addOnFailureListener { e -> Log.e("EventWorker", "Failed to update status", e) }
    }

}