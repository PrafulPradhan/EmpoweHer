package com.example.empoweher.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.empoweher.R
import com.example.empoweher.auth.signin.TypewriterText
import com.example.empoweher.composables.EventCard
import com.example.empoweher.composables.HeartAnimation
import com.example.empoweher.composables.QuestionCard
import com.example.empoweher.composables.getInfo
import com.example.empoweher.composables.getValue
import com.example.empoweher.composables.onBoarding
import com.example.empoweher.composables.slider
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.viewmodel.mainviewmodel
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import com.android.volley.Request
import kotlinx.coroutines.delay
import org.json.JSONArray
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.composables.getInfoUser
import com.google.firebase.database.FirebaseDatabase


var schemesArray=JSONArray()

fun fetchJsonData(context: Context, url: String, onSuccess: (JSONObject) -> Unit, onError: (String) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    Log.d("Hii Outside","dhruv")
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            try {
                schemesArray = response.getJSONArray("schemes")
                for (i in 0 until schemesArray.length()) {
                    val scheme = schemesArray.getJSONObject(i)
                    val name = scheme.getString("name")
                    val link = scheme.getString("link")
                    // Log or process the data
                    Log.d("SCHEME_INFO", "Name: $name, Link: $link")
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", "Error parsing JSON: ${e.message}")
            }
        },
        { error ->
            Log.e("VOLLEY_ERROR", "Request failed: ${error.message}")
        }
    )
    // Add the request to the queue
    queue.add(jsonObjectRequest)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun Home(navigateToNextScreen: (route: String)->Unit) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    var userId = ""
    if (user != null) {
        userId = user.uid
        // Use the userId
    }
    val dp = getInfoUser(thing = "Dp", userId = userId)
    val image = rememberAsyncImagePainter(dp)

    val scrollState = rememberScrollState()
    var currentFirebaseUser ="PCAPS"
    var schemes=JSONObject()
    try {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        fetchJsonData(
            context = context,
            url = "https://scrapeapi-aerf.onrender.com/get_schemes",
            onSuccess = { jsonResponse ->
                schemes=jsonResponse
            },
            onError = { error ->
                Log.d("Schemes", "Error : $error")
            }
        )
    }
    catch (e:Exception){

    }
    Column(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(colorResource(id = R.color.cream))
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.pale_brown))
        ){
            Box(modifier = Modifier
                .size(converterHeight(70, context).dp)
                .padding(converterHeight(5, context).dp)) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.agarbati),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )
            }
            TypewriterText(texts = listOf("Welcome to Agati"),Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .size(converterHeight(70, context).dp)
                .padding(converterHeight(5, context).dp)
                .clickable {
                    navigateToNextScreen(Screen.Profile.route + "/" + currentFirebaseUser)
                }) {
                Image(
                    painter= image,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(converterHeight(120,context).dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Box(modifier= Modifier
            .fillMaxWidth()
            .height(converterHeight(400, context).dp)
            .padding(converterHeight(10, context).dp)
        )
        {
            slider()
        }
        Column(modifier= Modifier
            .padding(converterHeight(10, context).dp)
            .height(converterHeight(200, context).dp)
            .clip(RoundedCornerShape(converterHeight(10, context).dp))
            .background(colorResource(id = R.color.lightorange))
            .verticalScroll(rememberScrollState())
            .border(width = 2.dp, color = colorResource(id = R.color.lightpurple))
        ) {
            Text(text = "Recent Schemes",
                fontSize = converterHeight(20,context).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = converterHeight(5, context).dp)
            )
            for (i in 0 until schemesArray.length()) {
                val scheme = schemesArray.getJSONObject(i)
                val name = scheme.getString("name")
                val link = scheme.getString("link")
                Log.d("Schemes","s")
                SchemeCard(schemeName = name, link)
                Log.d("Schemes","n")
            }
        }
        Column(
            modifier= Modifier
                .padding(converterHeight(10, context).dp)
                .clip(RoundedCornerShape(converterHeight(10, context).dp))
                .background(colorResource(id = R.color.lightblue))
        ) {
//            FloatingActionButtonExample(navigateToNextScreen)
            Text(text = "Recommended Events",
                fontSize = converterHeight(20,context).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = converterHeight(5, context).dp),
                color=Color.White
            )
            val eventId="-Njp7ySPE-z629UhUxVk"
            val eventImage= "https://firebasestorage.googleapis.com/v0/b/empowerher-39d60.appspot.com/o/b0Yra1fWvHbPuGJgsijrfFvHaSD2%2F2023-11-22T09%3A17%3A32.545967?alt=media&token=f58cb4fa-9b31-487f-a59e-b7ca13440502"
            val eventTag= "Empowerment"
            val eventName= "TCS CareerNext"
            val eventCost="250"
            EventCard(navigateToNextScreen = navigateToNextScreen,eventId=eventId,eventCost=eventCost, eventTag = eventTag, eventImage = eventImage, eventTitle = eventName)
        }
        Column(
            modifier= Modifier
                .padding(converterHeight(10, context).dp)
                .clip(RoundedCornerShape(converterHeight(10, context).dp))
                .background(colorResource(id = R.color.emeraldgreen))

        ) {
            Text(text = "Top Questions",
                fontSize = converterHeight(20,context).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                textAlign = TextAlign.Center,
                modifier=Modifier.fillMaxWidth()
                    .padding(top = converterHeight(5, context).dp),
                color=Color.White
            )
            val questionId="-NqG5OKIeJ8EXxsUsR4z"
            val question="How do you empower yourself and the women around you"
            val designation="student"
            val tag= "Empowerment"
            val userId="24Si2cNeD8Uq7vIbGCTDUSAHNOg1"
            val userName="Aman"
            QuestionCard(navigateToNextScreen = navigateToNextScreen, questionId = questionId, question = question, profession = designation, userId = userId, userName = userName)
        }
    }
    Box(modifier = Modifier.height(converterHeight(300,context).dp))
}

@Composable
fun SchemeCard(schemeName:String,uriString:String){
    val context= LocalContext.current
    Card(modifier= Modifier
        .fillMaxWidth()
        .padding(converterHeight(10, context).dp),
        colors=CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        )
        ){
        Text(text = schemeName,
            modifier= Modifier
                .fillMaxWidth()
                .padding(converterHeight(5, context).dp)
                .clickable {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(uriString)
                    )
                    context.startActivity(urlIntent)
                },
            fontSize = converterHeight(17,context).sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun FloatingActionButtonExample(navigateToNextScreen: (route: String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()
        .zIndex(1F)) {
        FloatingActionButton(
            onClick = {navigateToNextScreen(Screen.ChatBot.route)},
            modifier = Modifier
                .align(Alignment.BottomEnd),
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbot),
                contentDescription = "Login Pic",
                modifier = Modifier
                    .size(40.dp, 40.dp),
                contentScale = ContentScale.Fit,

            )
        }
    }
}
