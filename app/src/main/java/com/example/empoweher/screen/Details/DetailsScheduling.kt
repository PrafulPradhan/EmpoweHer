package com.example.empoweher.screen.Details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.composables.SampleText

@Composable
fun DetailsScheduling(navigateToNextScreen: (route: String)->Unit){
    val list = mutableListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Text(
        text = "Availability",
        fontSize = converterHeight(25, LocalContext.current).sp,
        fontFamily = FontFamily(Font(R.font.font1)),
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.black)
    )

    LazyColumn(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(list){each->
            switchItem(each)
        }
    }
}

@Composable
fun switchItem(day:String){
    var checked by remember { mutableStateOf(false) }
    var addTime by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()
        .background(colorResource(id = R.color.cream))){
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                addTime = !addTime
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(text=day, modifier = Modifier.offset(y=10.dp))
    }

    if(addTime){
        Text(text="Hi")
    }
}