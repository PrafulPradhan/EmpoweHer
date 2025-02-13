package com.example.empoweher.composables

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.empoweher.viewmodel.TimingViewModel
import com.example.empoweher.viewmodel.mainviewmodel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun Timings(navigateToNextScreen: (route: String)->Unit) {
    val context= LocalContext.current
    val weeks= mapOf("0" to "Sunday","1" to "Monday","2" to "Tuesday","3" to "Wednesday","4" to "Thursday","5" to "Friday","6" to "Saturday")

    var currentUser by remember{
        mutableStateOf("24Si2cNeD8Uq7vIbGCTDUSAHNOg1")
    }

    var currentFirebaseUser by remember{
        mutableStateOf("")
    }
    try {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid

    }
    catch (e:Exception){

    }

    if (currentFirebaseUser!=null && currentFirebaseUser!=""){
        currentUser=currentFirebaseUser
    }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val scrollState = rememberScrollState()
    val calendar = Calendar.getInstance()
    var timeMs by remember{
        mutableStateOf(calendar.time)
    }
    val dayOfWeek = dayFormat.format(calendar.time)
    val dayNumber = weeks.entries.find { it.value == dayOfWeek }?.key
    var dayIndex by remember{
        mutableStateOf(dayNumber!!.toInt())
    }


    val viewModel = viewModel { TimingViewModel(currentFirebaseUser) }
    when( val result= viewModel.response.value){
        is DataState.Loading -> {
            Log.d("slots","inside loading")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream)),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier= Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.event_loading_page),
                        contentDescription = "cd"
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "Events Loading  ",
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                fontWeight= FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.font1))
                            )
                            LoadingAnimation3()
                        }
                    }
                }
            }
        }
        is DataState.SuccessSlots -> {
         val slots = result.data
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    weeks[dayIndex.toString()]?.let { Text(
                        text = it,
                        fontSize = converterHeight(25, LocalContext.current).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black),
                        modifier = Modifier.padding(top = converterHeight(15, LocalContext.current).dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    ) }
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
                            modifier = Modifier.padding(
                                top = converterHeight(
                                    15,
                                    LocalContext.current
                                ).dp, start = converterHeight(18, LocalContext.current).dp
                            )
                                .align(Alignment.Start)
                        )
                        key(dayIndex) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            ) {
                                items(slots.slice((dayIndex * 12)..((dayIndex * 12) + 5))) { each ->
                                    scheduleItem(
                                        each.start!!,
                                        each.end!!,
                                        each.status!!,
                                        each.key!!,
                                        each.day!!,
                                        each.index!!,
                                    )
                                }
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
                        key(dayIndex){
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            ) {
                                items(slots.slice((dayIndex*12+6)..((dayIndex*12)+11))) { each ->
                                    scheduleItem(
                                        each.start!!,
                                        each.end!!,
                                        each.status!!,
                                        each.key!!,
                                        each.day!!,
                                        each.index!!,
                                    )
                                }
                            }


                            }

                        }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "prev",
                            modifier = Modifier.clickable{
                                if (dayIndex.toInt()==0){
                                    dayIndex=6

                                }
                                else{
                                    dayIndex=((dayIndex.toInt()-1) % 7)
                                }

                            })
                                Text(text = dateFormat.format(timeMs))
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "next",
                            modifier = Modifier.clickable{
                                if (dayIndex!!.toInt()==6){
                                    dayIndex=0

                                }
                                else{
                                    dayIndex=((dayIndex!!.toInt()+1) % 7)
                                }

                            })

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
            Log.d("slots","inside error")
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