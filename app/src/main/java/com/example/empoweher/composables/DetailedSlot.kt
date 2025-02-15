package com.example.empoweher.composables

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.activities.Payment
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.empoweher.activities.VideoConferencing
import com.example.empoweher.model.Slot
import com.google.gson.Gson

@Composable
fun DetailedSlot(navigateToNextScreen: (route: String)->Unit, jsonSlot:String){

    val slotObject = Gson().fromJson(jsonSlot, Slot::class.java)
    Log.d("aman", slotObject.toString())
    var meetingNo by remember {
        mutableStateOf("")
    }
    meetingNo= getInfoUser("Schedule/${slotObject.day}/${slotObject.key}/meetingId",slotObject.e_id)

    var booked by remember{
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.lightorange)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Text(
                "Slot Booking", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(R.font.font1)
                ),
                fontWeight = FontWeight.Bold, fontSize = 30.sp
            )
        }

        val startTime = ""

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.emeraldgreen)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Text(
                slotObject.key!!, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(R.font.font1)
                ),
                fontWeight = FontWeight.Bold, fontSize = 30.sp
            )
        }

        val context = LocalContext.current
        val meetingId = (111111111..999999999).random().toString()

        if(meetingNo=="null") {
            ElevatedButton(onClick = {
                val intent = Intent(context, Payment::class.java)
                intent.putExtra(
                    "slotPath",
                    "${slotObject.e_id}/Schedule/${slotObject.day}/${slotObject.key}/status"
                )
                intent.putExtra(
                    "userPath",
                    "${slotObject.e_id}/Schedule/${slotObject.day}/${slotObject.key}/u_id"
                )
                intent.putExtra(
                    "meetingPath",
                    "${slotObject.e_id}/Schedule/${slotObject.day}/${slotObject.key}/meetingId"
                )
                intent.putExtra("userId", slotObject.u_id)
                intent.putExtra("meetingId", meetingId)
                context.startActivity(intent)
                booked = true
            }) {
                Text("Book")
            }
        }

        val name = getInfoUser("name", currentFirebaseUser)

        if (meetingNo!="null" && (currentFirebaseUser == slotObject.e_id || currentFirebaseUser==slotObject.u_id)) {
            ElevatedButton(onClick = {
                if(meetingNo!="") {
                    val navigate = Intent(context, VideoConferencing::class.java)
                    navigate.putExtra("meetId", meetingNo)
                    navigate.putExtra("name", name)
                    context.startActivity(navigate)
                }
            }) {
                Text("Join Now")
            }
        }
    }
}