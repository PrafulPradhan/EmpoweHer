package com.example.empoweher.screen.voicebot

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.empoweher.R
import android.content.Context

fun Response(input:String, context:Context): String {

    Log.d("PCAPS", input)
    val keywords = arrayOf("hi", "khana", "bye")
    val sections = mapOf(
        "hi" to R.raw.hi,
        "khaana" to R.raw.khaana,
        "bye" to R.raw.bye
    )
    val words = input.split(" ")
    for(word in words){
        Log.d("PCAPS", word)
        if(word in keywords){
            if(word == "hi"){
                val mediaPlayer = MediaPlayer.create(context,R.raw.hi)
                mediaPlayer.start()
            }
        }
    }
    return "Juice Pilado"
}