package com.example.empoweher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.composables.SampleText
import com.example.empoweher.screen.ask.Navigation
import java.util.Random
import java.util.UUID


class VideoConferencing : AppCompatActivity() {
    @Composable
    fun Video(context: Context){
        var name by remember{
            mutableStateOf("")
        }
        var meetId by remember{
            mutableStateOf("")
        }
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Video Conference", fontSize = 26.sp, color = colorResource(id = R.color.black),
                modifier = Modifier.padding(start = 20.dp))
            OutlinedTextField(
                value = meetId,
                label = { Text("Enter Meeting Id") },
                onValueChange = { str ->
                    meetId = str
                },
                modifier=Modifier.padding(5.dp)
            )
            OutlinedTextField(
                value = name,
                label = { Text("Enter Your Name") },
                onValueChange = { str ->
                    name = str
                },
                modifier=Modifier.padding(5.dp)
            )
            Button(onClick = {
                startMeeting(meetId,name, context)
            }) {
                Text(text = "Join Meet")
            }
            Button(onClick = {
                startMeeting(getRandomMeetingId(),name, context)
            }) {
                Text(text = "Create Meet")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
//            Video(context = this@VideoConferencing)
            val meetId = intent.getStringExtra("meetId")
            val name = intent.getStringExtra("name")
            if (meetId != null && name != null) {
                startMeeting(meetId, name, this)
            }
        }
    }

    private fun getRandomMeetingId(): String {
        val id:StringBuilder=java.lang.StringBuilder()
        while (id.length!=10){
            val random=Random().nextInt(10)
            id.append(random)
        }
        return id.toString()
    }
    fun startMeeting(meetingId:String, name:String, context: Context){
        val userID:String=UUID.randomUUID().toString()
        val intent=Intent(context,Navigation::class.java)
        intent.putExtra("meeting_id",meetingId)
        intent.putExtra("name",name)
        intent.putExtra("user_id",userID)
        startActivity(intent)
    }
}
