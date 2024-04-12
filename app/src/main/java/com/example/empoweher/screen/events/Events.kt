package com.example.empoweher.screen.events

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empoweher.composables.EventCard
import com.example.empoweher.R
import com.example.empoweher.composables.SampleText
import com.example.empoweher.composables.getInfoUser
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Event
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.screen.ask.TagButton
import com.example.empoweher.viewmodel.QuestionViewModel
import com.example.empoweher.viewmodel.mainviewmodel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun Events(navigateToNextScreen: (route: String)->Unit){

    val context= LocalContext.current

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

    var flag by remember{
        mutableStateOf(false)
    }

    if (getInfoUser(thing = "flag", userId =currentUser )=="1"){
        flag=true
    }

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
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = converterHeight(10, context = context).dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.teal_700),
                    ),
                    shape = RoundedCornerShape(converterHeight(30,context).dp),
                ) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .horizontalScroll(rememberScrollState())
                                .clip(RoundedCornerShape(converterHeight(20, context).dp))
                        ) {
                            Button(onClick = {

                                       navigateToNextScreen(Screen.BookedEvents.route)

                            },
                                modifier = Modifier.padding(start = converterHeight(5, LocalContext.current).dp)
                            ) {
                                SampleText(text = "Booked",16, colorResource(id = R.color.white))
                            }
                            TagButtonEvent(tag = "Educational", viewModel = viewModel)
                            TagButtonEvent(tag = "Exploratory", viewModel = viewModel)
                            TagButtonEvent(tag = "Defence", viewModel = viewModel)
                            TagButtonEvent(tag = "Discussion", viewModel = viewModel)
                            TagButtonEvent(tag = "Empowerment", viewModel = viewModel)
                            TagButtonEvent(tag = "Others", viewModel = viewModel)

                        }


                        Spacer(modifier = Modifier.weight(1f))
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(20.dp, 10.dp)
                                .size(50.dp),
                            shape = CircleShape,
                            onClick = {
                                if (flag){
                                    navigateToNextScreen(Screen.EventForm.route)
                                }
                                else{
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSeBf0Ld60zVD0a4t3g36kFiYs232XCD8HfoI7z8Yt1mjMjhfg/viewform")
                                    ).setPackage("com.android.chrome")
                                    context.startActivity(intent)
                                }

                            },
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                "Floating action button.",
                                modifier = Modifier.size(50.dp)
                            )
                        }

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
fun ShowLazyList(event: MutableList<Event>,navigateToNextScreen: (route: String)->Unit) {
    LazyColumn(modifier= Modifier
        .clip(shape = RoundedCornerShape(25.dp))
        .fillMaxHeight(0.9f)
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
@Composable
fun TagButtonEvent(tag:String,viewModel: mainviewmodel){
    Button(onClick = {
        viewModel.fetch(tag)

    },
        modifier = Modifier.padding(start = converterHeight(5, LocalContext.current).dp)
    ) {
        SampleText(text = tag,16, colorResource(id = R.color.white))
    }

}