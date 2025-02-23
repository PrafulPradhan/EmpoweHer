package com.example.empoweher.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.empoweher.R
import org.json.JSONArray

class Model : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_model)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }
        setContent {
            val py= Python.getInstance()
            val module=py.getModule("logistic")
            Log.d("Raja Mausa","logistic")
//            val eventImage=intent.getStringExtra("eventImage")
            val jsonString=intent.getStringExtra("params")
            val jsonArray=JSONArray(jsonString)

            val obj = module.callAttr("predict_from_json", jsonArray)
            Log.d("Raja Mausa", obj.toString())
        }
    }
}