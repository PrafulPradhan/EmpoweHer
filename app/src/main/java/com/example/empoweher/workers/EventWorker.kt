package com.example.empoweher.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class EventWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object{
        var isStopped=false
    }
    init {
        Companion.isStopped =false
    }
    override fun doWork(): Result {
        var inputData: Data = inputData

        return Result.success()
    }
}