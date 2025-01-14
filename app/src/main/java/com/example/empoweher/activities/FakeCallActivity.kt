package com.example.empoweher.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.empoweher.R
import com.google.android.material.internal.ContextUtils.getActivity

class FakeCallActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fake_call)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        if (getActivity(this)?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.RECORD_AUDIO)
            } != PackageManager.PERMISSION_GRANTED) {
            getActivity(this)?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.RECORD_AUDIO),0
                )
            };
        }

        setContent {
            Log.d("Raja Mausa", "before")
            val py= Python.getInstance()
            val module=py.getModule("app")
            Log.d("Raja Mausa","after")
//            val eventImage=intent.getStringExtra("eventImage")

            val obj = module.callAttr("driver")
        }
    }
}