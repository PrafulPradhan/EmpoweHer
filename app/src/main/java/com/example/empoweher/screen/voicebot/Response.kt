package com.example.empoweher.screen.voicebot

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.empoweher.R
import android.content.Context

fun Response(input: String, context: Context): String {

    Log.d("PCAPS", input)
    val keywords = arrayOf("hi", "khana", "bye")
    val sections = mapOf(
        "hi" to R.raw.hi,
        "khana" to R.raw.khaana,
        "bye" to R.raw.bye
    )
    val words = input.split(" ")
    var mediaPlayer: MediaPlayer? = null

    for (word in words) {
        Log.d("PCAPS", word)
        if (word in keywords) {
            // Stop and release any existing MediaPlayer
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }

            // Create and start a new MediaPlayer
            mediaPlayer = sections[word]?.let { MediaPlayer.create(context, it) }
            if (mediaPlayer != null) {
                mediaPlayer.start()

                // Release the MediaPlayer after playback is complete
                mediaPlayer.setOnCompletionListener {
                    it.reset()
                    Log.d("PCAPS", "MediaPlayer released")
                }
            }
            else if(word == "khaana"){
                val mediaPlayer = MediaPlayer.create(context,R.raw.khaana)
                mediaPlayer.start()
            }
            else if(word=="bye"){
                    val mediaPlayer = MediaPlayer.create(context,R.raw.bye)
                    mediaPlayer.start()
            }
        }
    }

    return "Juice Pilado"
}
