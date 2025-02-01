package com.example.empoweher.screen.Details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
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
    Column(modifier=Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.cream))
    ) {
    Text(
        text = "Provide Your Schedule",
        fontSize = converterHeight(25, LocalContext.current).sp,
        fontFamily = FontFamily(Font(R.font.font1)),
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.black),
        modifier = Modifier.padding(top=converterHeight(15, LocalContext.current).dp, start = converterHeight(15, LocalContext.current).dp)
    )

    LazyColumn(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(list){each->
            switchItem(each)
        }
    }}
}

@Composable
fun switchItem(day:String){
    var checked by remember { mutableStateOf(false) }
    var addTime by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()){
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                addTime = !addTime
            },
            modifier=Modifier.padding(converterHeight(10, LocalContext.current).dp)
        )

        Spacer(modifier = Modifier.width(converterHeight(10, LocalContext.current).dp))

        Text(text=day,
            modifier = Modifier.offset(y=converterHeight(22, LocalContext.current).dp),
            fontSize = converterHeight(18, LocalContext.current).sp
        )
    }
    var startTime by remember {
        mutableStateOf("")
    }
    var endTime by remember {
        mutableStateOf("")
    }
    val context= LocalContext.current
    if(addTime){
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
            ){
            OutlinedTextField(
                value = startTime,
                textStyle = LocalTextStyle.current.merge(
                    TextStyle(
                        fontSize = converterHeight(
                            20,
                            context
                        ).sp
                    )
                ),
                onValueChange = { str ->
                    startTime = str
                })
            OutlinedTextField(
                value = endTime,
                textStyle = LocalTextStyle.current.merge(
                    TextStyle(
                        fontSize = converterHeight(
                            20,
                            context
                        ).sp
                    )
                ),
                onValueChange = { str ->
                    endTime = str
                })
        }
    }
}