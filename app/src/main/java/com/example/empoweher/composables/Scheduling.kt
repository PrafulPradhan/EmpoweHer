package com.example.empoweher.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.screen.Details.converterHeight

@Composable
fun Scheduling(navigateToNextScreen: (route: String)->Unit){
    Column(modifier = Modifier.fillMaxSize()
        .background(colorResource(R.color.cream))){
        Text(
            text = "Sunday",
            fontSize = converterHeight(25, LocalContext.current).sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
            modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Morning",
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp)
                    .align(Alignment.Start)
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                scheduleItem("9:00", "10:00", "available")
                scheduleItem("10:00", "11:00", "available")
                scheduleItem("11:00", "12:00", "available")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                scheduleItem("13:00", "14:00", "available")
                scheduleItem("14:00", "15:00", "available")
                scheduleItem("15:00", "16:00", "available")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Evening",
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(top= converterHeight(15, LocalContext.current).dp)
                    .align(Alignment.Start)
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                scheduleItem("13:00", "14:00", "available")
                scheduleItem("14:00", "15:00", "available")
                scheduleItem("15:00", "16:00", "available")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                scheduleItem("13:00", "14:00", "available")
                scheduleItem("14:00", "15:00", "available")
                scheduleItem("15:00", "16:00", "available")
            }
        }
    }
}

@Composable
fun scheduleItem(start:String, end:String, status:String){
    var color: Color = colorResource(R.color.light_gray)
    if(status == "available"){
        color = colorResource(R.color.emeraldgreen)
    }
    if(status == "occupied"){
        color = colorResource(R.color.redorange)
    }
    Box(modifier = Modifier.height(40.dp)
        .width(100.dp)
        .background(color = color)
        .clickable {

        }){
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = start,
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                color = colorResource(R.color.white)
            )

            Text(
                text = "-",
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                color = colorResource(R.color.white)
            )

            Text(
                text = end,
                fontSize = converterHeight(20, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                color = colorResource(R.color.white)
            )
        }
    }
}