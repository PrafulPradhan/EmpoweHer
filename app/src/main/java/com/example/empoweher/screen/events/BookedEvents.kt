package com.example.empoweher.screen.events

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.empoweher.composables.SampleText
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.viewmodel.bookedEvents
import com.example.empoweher.viewmodel.convertToMillis

@Composable
fun BookedEvents(navigateToNextScreen: (route: String)->Unit){
    val viewModel = viewModel { bookedEvents() }
    val context= LocalContext.current
    when( val result= viewModel.response.value){
        is DataState.Loading -> {
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
        is DataState.Success -> {
            val eventsOngoing= mutableListOf<Event>()
            val eventsCompleted= mutableListOf<Event>()
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream)),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                for (i in result.data){
                    val millis= convertToMillis(i.endDate!!,i.timing!!)
                    val currentMillis=System.currentTimeMillis()
                    Log.d("millis",millis.toString())
                    Log.d("currentmillis",currentMillis.toString())
                    if (currentMillis>=millis){
                        eventsCompleted.add(i)
                    }else{
                        eventsOngoing.add(i)
                    }
                }
                Log.d("eventsCompleted",eventsCompleted.toString())
                Log.d("eventsOngoing",eventsOngoing.toString())
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(5.dp)
                    ) {
                    SampleText("Ongoing", fontSize = 18)
                    ShowLazyList(eventsOngoing, navigateToNextScreen)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(5.dp)
                ) {
                    SampleText("Completed", fontSize = 18)
                    ShowLazyList(eventsCompleted, navigateToNextScreen)
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