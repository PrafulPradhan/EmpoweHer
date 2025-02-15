package com.example.empoweher.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

val weeks= mapOf("0" to "Sunday","1" to "Monday","2" to "Tuesday","3" to "Wednesday","4" to "Thursday","5" to "Friday","6" to "Saturday")
val _selectedSlots = MutableStateFlow(List(12) { false })  // 12 buttons
val selectedSlots: StateFlow<List<Boolean>> = _selectedSlots.asStateFlow()

val calendar = Calendar.getInstance()
val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

val currentDate = dateFormat.format(calendar.time)
val dayOfWeek = dayFormat.format(calendar.time)
val dayNumber = weeks.entries.find { it.value == dayOfWeek }?.key

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun Scheduling(navigateToNextScreen: (route: String)->Unit){

    var date by remember {
        mutableStateOf(currentDate)
    }

    var day by remember {
        mutableStateOf(dayOfWeek)
    }

    var dayIndex by remember {
        mutableStateOf(dayNumber)
    }


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
                weeks.entries.find { it.value == weeks[dayIndex]!!}?.let {
                    Text(
                        text = it.key,
                        fontSize = converterHeight(25, LocalContext.current).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black),
                        modifier = Modifier.padding(top = converterHeight(15, LocalContext.current).dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Card(modifier=Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .size(160.dp)
                ) {
                    Text(
                        text = "Morning" + dayIndex,
                        fontSize = converterHeight(20, LocalContext.current).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black),
                        modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp,start= converterHeight(18, LocalContext.current).dp)
                            .align(Alignment.Start)
                    )
//                    var currentSlotsMorning = remember {
//                        mutableListOf<Slot>()
//                    }
                    var currentSlotsMorning = remember {
                        mutableStateListOf<Slot>()
                    }
                    LaunchedEffect(key1=dayIndex) {
                        for(i in 0..5){
                            currentSlotsMorning.add(slotMorning[dayIndex!!.toInt()][i])
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        items(currentSlotsMorning) { each ->
                            scheduleItem(
                                each.start!!,
                                each.end!!,
                                each.status!!,
                                each.key!!,
                                each.day!!,
                                each.index!!,
                                each.e_id!!,
                                navigateToNextScreen
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
                    val currentSlotsEvening=mutableListOf<Slot>()
                    for(i in 0..5){
                        val each= slotEvening[dayIndex!!.toInt()][i]
                        currentSlotsEvening.add(each)
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {

                        items(currentSlotsEvening) { each ->
                            scheduleItem(
                                each.start!!,
                                each.end!!,
                                each.status!!,
                                each.key!!,
                                each.day!!,
                                each.index!!,
                                each.e_id!!,
                                navigateToNextScreen
                            )
                        }
                    }
                }


                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "prev",
                        modifier = Modifier.clickable{
                            if (dayIndex!!.toInt()==0){
                                dayIndex="6"

                            }
                            else{
                                dayIndex=((dayIndex!!.toInt()-1) % 7).toString()
                            }
                            slotEvening.clear()
                            slotMorning.clear()

                        })
                    Text(text = date!!)
                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "next",
                        modifier = Modifier.clickable{
                            if (dayIndex!!.toInt()==6){
                                dayIndex="0"

                            }
                            else{
                                dayIndex=((dayIndex!!.toInt()+1) % 7).toString()
                            }
                            slotEvening.clear()
                            slotMorning.clear()


                        })
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
fun scheduleItem(start:String, end:String, status:String,key:String,day:String, index:String,userId:String,navigateToNextScreen: (route: String) -> Unit){
    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");
    val currentFirebaseUser=FirebaseAuth.getInstance().currentUser!!.uid
    var color by remember{
        mutableStateOf(R.color.white)
    }

    var status by remember{
        mutableStateOf("")
    }
    val weekday = weeks.entries.find { it.value == day }?.key
    status= getInfoUser("/Schedule/$weekday/$key/status",userId)

    val context= LocalContext.current

    if(currentFirebaseUser==userId){
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
            val slot = Slot(e_id=userId, u_id=currentFirebaseUser, start=start, end=end, status=status, key=key, day=weekday, index=index)
            val jsonSlot = Gson().toJson(slot)
            if(status=="undefined") {
                dbref.child(userId).child("Schedule").child(weekday!!).child(key).child("status").setValue("available").addOnSuccessListener {
                    color = R.color.emeraldgreen
                }

            }
            else if(status=="available"){

                dbref.child(userId).child("Schedule").child(weekday!!).child(key).child("status").setValue("undefined").addOnSuccessListener {
                    color = R.color.white
                }
            }
            else{
                if (status=="occupied") {
                    color = R.color.light_gray
                    navigateToNextScreen(Screen.DetailSlot.route+"/"+jsonSlot)

                }
            }


        },
        ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
}

fun getAdjacentDate(inputDate: String, daysOffset: Int): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(inputDate) ?: return "Invalid Date"

    // Add or subtract days
    calendar.add(Calendar.DAY_OF_MONTH, daysOffset)

    return dateFormat.format(calendar.time)
}

fun getAdjacentDay(inputDate: String, daysOffset: Int): String {
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(inputDate) ?: return "Invalid Date"

    // Adjust the date
    calendar.add(Calendar.DAY_OF_MONTH, daysOffset)

    return dayFormat.format(calendar.time)
}