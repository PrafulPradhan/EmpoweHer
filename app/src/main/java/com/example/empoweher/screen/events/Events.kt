package com.example.empoweher.screen.events

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empoweher.composables.EventCard
import com.example.empoweher.R
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.Screen
import com.example.empoweher.viewmodel.mainviewmodel
import kotlinx.coroutines.delay

@Composable
fun Events(navigateToNextScreen: (route: String)->Unit){
    val viewModel = viewModel { mainviewmodel() }
    when( val result= viewModel.response.value){
        is DataState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream)),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier=Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
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
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream)),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                ShowLazyList(result.data,navigateToNextScreen)
                Spacer(modifier = Modifier.height(20.dp))
                FloatingActionButton(
                    modifier=Modifier
                        .align(Alignment.End)
                        .padding(20.dp,10.dp)
                        .size(80.dp),
                    shape = CircleShape,
                    onClick = {
                              navigateToNextScreen(Screen.EventForm.route)
                    },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.",modifier=Modifier.size(50.dp))
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
fun ShowLazyList(event: MutableList<Event>,navigateToNextScreen: (route: String)->Unit) {
    LazyColumn(modifier= Modifier
        .fillMaxHeight(0.8f)
        .fillMaxWidth()
        .background(colorResource(id = R.color.cream))){
       items(event){each->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .padding(5.dp)
                    .clickable {

                    },
            ) {
                EventCard(
                    eventId=each.eventId!!,
                    eventTitle = each.eventName!!,
                    eventCity=each.city!!,
                    eventCapacity = each.capacity!!,
                    eventStartDate = each.startDate!!,
                    eventEndDate = each.endDate!!,
                    eventTiming = each.timing!!,
                    eventCost = each.eventCost!!,
                    eventImage = each.eventImage!!,
                    navigateToNextScreen = navigateToNextScreen,
                    eventTag=each.tag!!
                )
            }
        }
    }
}

@Composable
fun LoadingAnimation3(
    circleColor: Color = Color(0xFF35898F),
    circleSize: Dp = 15.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f
) {

    // 3 circles
    val circles = listOf(
        remember {
            androidx.compose.animation.core.Animatable(initialValue = initialAlpha)
        },
        remember {
            androidx.compose.animation.core.Animatable(initialValue = initialAlpha)
        },
        remember {
            androidx.compose.animation.core.Animatable(initialValue = initialAlpha)
        }
    )

    circles.forEachIndexed { index, animatable ->

        LaunchedEffect(Unit) {

            // Use coroutine delay to sync animations
            delay(timeMillis = (animationDelay / circles.size).toLong() * index)

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    // container for circles
    Row(
        modifier = Modifier
        //.border(width = 2.dp, color = Color.Magenta)
    ) {

        // adding each circle
        circles.forEachIndexed { index, animatable ->

            // gap between the circles
            if (index != 0) {
                Spacer(modifier = Modifier.width(width = 6.dp))
            }

            Box(
                modifier = Modifier
                    .size(size = circleSize)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = animatable.value)
                    )
            ) {
            }
        }
    }
}

//%2F ---->For /
//%3A ----->For :
