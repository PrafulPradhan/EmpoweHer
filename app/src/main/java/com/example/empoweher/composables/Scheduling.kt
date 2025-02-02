package com.example.empoweher.composables

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empoweher.R
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Screen
import com.example.empoweher.model.Slot
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.screen.events.LoadingAnimation3
import com.example.empoweher.screen.events.ShowLazyList
import com.example.empoweher.screen.events.TagButtonEvent
import com.example.empoweher.viewmodel.SlotViewModel
import com.example.empoweher.viewmodel.mainviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

val weeks= mapOf("0" to "Sunday","1" to "Monday","2" to "Tuesday","3" to "Wednesday","4" to "Thursday","5" to "Friday","6" to "Saturday")
@Composable
fun Scheduling(navigateToNextScreen: (route: String)->Unit){
    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");
    val currentFirebaseUser=FirebaseAuth.getInstance().currentUser!!.uid
    val isEnt= getInfoUser("isEnt",currentFirebaseUser)
    val slots= listOf("9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00")
    val day="Sunday"
    val context=LocalContext.current
    val viewModel = viewModel { SlotViewModel() }
    when( val result= viewModel.response.value){
        is DataState.Loading->{

        }
        is DataState.SuccessSlot -> {
            val slotMorning=result.data
            val slotEvening=result.data2

            Column(modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.cream))) {
                Text(
                    text = day,
                    fontSize = converterHeight(25, LocalContext.current).sp,
                    fontFamily = FontFamily(Font(R.font.font1)),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black),
                    modifier = Modifier.padding(top = converterHeight(15, LocalContext.current).dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Card(modifier=Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .size(160.dp)
                ) {
                    Text(
                        text = "Morning",
                        fontSize = converterHeight(20, LocalContext.current).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black),
                        modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp,start= converterHeight(18, LocalContext.current).dp)
                            .align(Alignment.Start)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        items(slotMorning) { each ->
                            scheduleItem(
                                each.start!!,
                                each.end!!,
                                each.status!!,
                                each.key!!,
                                each.day!!
                            )
                        }
                    }
                }

                Card(modifier=Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .size(160.dp)
                ) {
                    Text(
                        text = "Evening",
                        fontSize = converterHeight(20, LocalContext.current).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black),
                        modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp,start= converterHeight(18, LocalContext.current).dp)
                            .align(Alignment.Start)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        items(slotEvening) { each ->
                            scheduleItem(
                                each.start!!,
                                each.end!!,
                                each.status!!,
                                each.key!!,
                                each.day!!
                            )
                        }
                    }
                }
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                    Button(
                        onClick = {
                            dbref.child(currentFirebaseUser).child("Schedule").setValue(weeks)
                            val slots= listOf("9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00")
                            val slotStart= listOf("9:00","10:00","11:00","12:00","13:00","14:00","16:00","17:00","18:00","19:00","20:00","21:00")
                            val slotEnd= listOf("10:00","11:00","12:00","13:00","14:00","15:00","17:00","18:00","19:00","20:00","21:00","22:00")
                            for(j in weeks.keys){
                                for(i in slots.indices){
                                    val slot= Slot(currentFirebaseUser,null,status="undefined", start = slotStart[i],end=slotEnd[i],key=slots[i],day=weeks[j])
                                    dbref.child(currentFirebaseUser).child("Schedule").child(j).child(slots[i]).setValue(slot)
                                }
                            }
                        },
                        modifier=Modifier.height(40.dp).width(100.dp)
                    ) {
                        Text("Reset",
                            fontSize = converterHeight(15, LocalContext.current).sp,
                            fontFamily = FontFamily(Font(R.font.font1)),
                            )
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context,"Your Schedule has been saved!!",Toast.LENGTH_SHORT).show()
                        },
                        modifier=Modifier.height(40.dp).width(100.dp)
                    ) {
                        Text("Save",
                            fontSize = converterHeight(15, LocalContext.current).sp,
                            fontFamily = FontFamily(Font(R.font.font1)),
                            )
                    }
                }
            }
        }
        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = 30.sp,
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                    fontSize = 30.sp,
                )
            }
        }
    }
}

@Composable
fun scheduleItem(start:String, end:String, status:String,key:String,day:String){
    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");
    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
    var color by remember{
        mutableStateOf(R.color.white)
    }
    val context= LocalContext.current
    val weekday = weeks.entries.find { it.value == day }?.key
    if(status == "available"){
        color = R.color.emeraldgreen
    }
    if(status == "occupied"){
        color = R.color.light_gray
    }
    Box(modifier = Modifier.height(40.dp)
        .width(converterHeight(130,context).dp)
        .clip(shape = RoundedCornerShape(25))
        .border(color= colorResource(R.color.darkgreen), width = 1.dp, shape =RoundedCornerShape(25))
        .background(color = colorResource(color))
        .clickable {
            if(color==R.color.white) {
                color = R.color.emeraldgreen
                dbref.child(currentFirebaseUser).child("Schedule").child(weekday!!).child(key).child("status").setValue("available")
            }
            else if(color==R.color.emeraldgreen){
                color = R.color.white
                dbref.child(currentFirebaseUser).child("Schedule").child(weekday!!).child(key).child("status").setValue("undefined")
            }
            else{
                if (status!="occupied") {
                    color = R.color.white
                }
            }
        },
        ){
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = start,
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
            )

            Text(
                text = "-",
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
            )

            Text(
                text = end,
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
            )
        }
    }
}