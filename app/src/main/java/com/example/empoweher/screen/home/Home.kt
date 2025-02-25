package com.example.empoweher.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.empoweher.R
import com.example.empoweher.auth.signin.TypewriterText
import com.example.empoweher.composables.EventCard
import com.example.empoweher.composables.QuestionCard
import com.example.empoweher.composables.slider
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import com.android.volley.Request
import org.json.JSONArray
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.example.empoweher.activities.Model
import com.example.empoweher.composables.getInfo
import com.example.empoweher.composables.getInfoUser
import com.example.empoweher.model.DataState
import com.example.empoweher.model.JsonUser
import com.example.empoweher.viewmodel.JsonUserDataViewModel
import com.example.empoweher.viewmodel.ProfileViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.merge


var schemesArray by mutableStateOf<JSONArray?>(null)

fun fetchJsonData(context: Context, url: String, onSuccess: (JSONObject) -> Unit, onError: (String) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    Log.d("Hii Outside","dhruv")
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            try {
                schemesArray = response.getJSONArray("schemes")
                for (i in 0 until (schemesArray?.length()!!)) {
                    val scheme = schemesArray?.getJSONObject(i)
                    val name = scheme?.getString("name")
                    val link = scheme?.getString("link")
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

fun sendJsonData(context: Context, url: String, mergedJson: JSONObject, onSuccess: (JSONObject) -> Unit, onError: (String) -> Unit) {
    val queue: RequestQueue = Volley.newRequestQueue(context)
    Log.d("Final", mergedJson.toString())
    // Creating the request body
    val jsonData = mergedJson.getJSONArray("jsonData")
    Log.d("Final", jsonData.toString())
    val jsonTarget = mergedJson.getJSONObject("jsonTarget")
    Log.d("Final", jsonTarget.toString())

    val requestBody = JSONObject()
    requestBody.put("jsonData", jsonData)
    requestBody.put("jsonTarget", jsonTarget)

    Log.d("Request Body", requestBody.toString())

    val jsonObjectRequest = object : JsonObjectRequest(
        Request.Method.POST, url, requestBody,
        Response.Listener { response ->
            try {
                Log.d("RESPONSE_SUCCESS", "Response: $response")
                onSuccess(response)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("JSON_ERROR", "Error parsing JSON: ${e.message}")
                onError("Error parsing response")
            }
        },
        Response.ErrorListener { error ->
            Log.e("VOLLEY_ERROR", "Request failed: ${error.message}")
            onError("Request failed: ${error.message}")
        }
    ) {
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            return headers
        }
    }
    // Add the request to the queue
    queue.add(jsonObjectRequest)
}

fun logisticModel(context: Context, url: String, jsonData: JSONArray, onSuccess: (JSONObject) -> Unit, onError: (String) -> Unit) {
    val queue: RequestQueue = Volley.newRequestQueue(context)
    Log.d("Final", jsonData.toString())
    val requestBody = JSONObject()
    requestBody.put("jsonData", jsonData)

    Log.d("Request Body Logistic Model", requestBody.toString())

    val jsonObjectRequest = object : JsonObjectRequest(
        Request.Method.POST, url, requestBody,
        Response.Listener { response ->
            try {
                Log.d("RESPONSE_SUCCESS", "Response: $response")
                onSuccess(response)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("JSON_ERROR", "Error parsing JSON: ${e.message}")
                onError("Error parsing response")
            }
        },
        Response.ErrorListener { error ->
            Log.e("VOLLEY_ERROR", "Request failed: ${error.message}")
            onError("Request failed: ${error.message}")
        }
    ) {
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            return headers
        }
    }
    // Add the request to the queue
    queue.add(jsonObjectRequest)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun Home(navigateToNextScreen: (route: String)->Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        fetchJsonData(
            context = context,
            url = "https://scrapeapi-aerf.onrender.com/get_schemes",
            onSuccess = { jsonResponse ->
//                schemes=jsonResponse
                schemesArray = jsonResponse.getJSONArray("schemes")
            },
            onError = { error ->
                Log.d("Schemes", "Error : $error")
            }
        )
    }

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

    }
    catch (e:Exception){
        Log.d("API", "${e}")
    }
    var education by remember { mutableStateOf(0) }
    var safety by remember { mutableStateOf(0) }
    var empowerment by remember { mutableStateOf(0) }
    var dailyGuidance by remember { mutableStateOf(0) }
    var arts by remember { mutableStateOf(0) }
    var technical by remember { mutableStateOf(0) }
    var socialAffairs by remember { mutableStateOf(0) }
    var childProblems by remember { mutableStateOf(0) }
    var astrology by remember { mutableStateOf(0) }
    var health by remember { mutableStateOf(0) }
    var spiritual by remember { mutableStateOf(0) }
    var history by remember { mutableStateOf(0) }
    var sports by remember { mutableStateOf(0) }
    var politics by remember { mutableStateOf(0) }
    var exploratory by remember { mutableStateOf(0) }
    var realEstate by remember { mutableStateOf(0) }
    var business by remember { mutableStateOf(0) }
    var price by remember { mutableStateOf(0) }
    var entertainment by remember { mutableStateOf(0) }
    var careerguidance by remember { mutableStateOf(0) }
    var recommendedEvents by remember  { mutableStateOf(JSONArray()) }
    val viewModel= viewModel{ ProfileViewModel() }
    when( val result= viewModel.response.value){
        is DataState.Loading -> {

        }
        is DataState.SuccessUser->{
            var userData = mutableListOf<JsonUser>()
            var targetUser: JsonUser? = null
            for(data in result.data){

                var userId = data.userID

                if(data.interests!!.contains("Education")){
                    education = 1
                }
                if(data.interests!!.contains("Safety")){
                    safety = 1
                }
                if(data.interests!!.contains("Empowerment")){
                    empowerment = 1
                }
                if(data.interests!!.contains("Daily Guidance")){
                    dailyGuidance = 1
                }
                if(data.interests!!.contains("Arts")){
                    arts = 1
                }
                if(data.interests!!.contains("Technical")){
                    technical = 1
                }
                if(data.interests!!.contains("Social Affairs")){
                    socialAffairs = 1
                }
                if(data.interests!!.contains("Child Problems")){
                    childProblems = 1
                }
                if(data.interests!!.contains("Astrology")){
                    astrology = 1
                }
                if(data.interests!!.contains("Health")){
                    health = 1
                }
                if(data.interests!!.contains("Spiritual")){
                    spiritual = 1
                }
                if(data.interests!!.contains("History")){
                    history = 1
                }
                if(data.interests!!.contains("Sports")){
                    sports = 1
                }
                if(data.interests!!.contains("Politics")){
                    politics = 1
                }
                if(data.interests!!.contains("Exploratory")){
                    exploratory = 1
                }
                if(data.interests!!.contains("Real Estate")){
                    realEstate = 1
                }
                if(data.interests!!.contains("Business")){
                    business = 1
                }
                if(data.interests!!.contains("Entertainment")){
                    entertainment = 1
                }
                if(data.interests!!.contains("Career Guidance")){
                    careerguidance = 1
                }
                var tuple = JsonUser(userId, education, safety, empowerment, dailyGuidance, arts, technical, socialAffairs, childProblems, astrology, health, spiritual, history, sports, politics, exploratory, realEstate, business, price)
                if(currentFirebaseUser == data.userID){
                    targetUser = JsonUser(currentFirebaseUser, education, safety, empowerment, dailyGuidance, arts, technical, socialAffairs, childProblems, astrology, health, spiritual, history, sports, politics, exploratory, realEstate, business, price)
                    continue
                }
                userData.add(tuple)
            }

            val gson = Gson()
            val jsonData = JSONArray(gson.toJson(userData))
            Log.d("test", jsonData.toString())
            val jsonTarget = JSONObject(gson.toJson(targetUser))
            val mergedJson = JSONObject()
            mergedJson.put("jsonData", jsonData)
            mergedJson.put("jsonTarget", jsonTarget)
            val viewModel2= viewModel{ JsonUserDataViewModel() }
            Log.d("test", jsonTarget.toString())
            sendJsonData(
                context,
                "https://modelapi-yz8c.onrender.com/predict",
                mergedJson,
                onSuccess = { response ->
                    Log.d("API_CALL", "Success: $response")
                    val similar_users=response.getJSONArray("similar_users")
                    Log.d("similar_users", "Success: $similar_users")
                    val jsonArray = JSONArray()
                    for (i in 0 until similar_users.length()) {
                        val userIdentify = similar_users[i].toString()
                        viewModel2.fetch(userIdentify) { data ->
                            for (i2 in data) {
                                val obj = JSONObject()
                                    .put("userID", userIdentify)
                                    .put("eventId", i2.eventId)
                                    .put("Education", education)
                                    .put("Safety", safety)
                                    .put("Empowerment", empowerment)
                                    .put("Daily Guidance", dailyGuidance)
                                    .put("Arts", arts)
                                    .put("Technical", technical)
                                    .put("Social Affairs", socialAffairs)
                                    .put("Child Problems", childProblems)
                                    .put("Astrology", astrology)
                                    .put("Health", health)
                                    .put("Spiritual", spiritual)
                                    .put("History", history)
                                    .put("Career Guidance", careerguidance)
                                    .put("Sports", sports)
                                    .put("Politics", politics)
                                    .put("Exploratory", exploratory)
                                    .put("Entertainment", entertainment)
                                    .put("Real Estate", realEstate)
                                    .put("Business", business)
                                    .put("Event_Domain", i2.eventDomain)
                                    .put("Price", i2.price)
                                    .put("Rating", i2.rating)
                                jsonArray.put(obj)
                            }

                            if (i == similar_users.length() - 1) {
                                Log.d("checkpoint", jsonArray.toString())

                                logisticModel(
                                    context = context,
                                    url = "https://modelapi-yz8c.onrender.com/logistic",
                                    jsonArray,
                                    onSuccess = { resp ->
                                        Log.d("RESPONSE_SUCC_LOGISTIC_Model", resp.toString())
                                        recommendedEvents = resp.getJSONArray("attended_users")
                                        Log.d("jsonArrayLogistic",recommendedEvents.toString())
                                    },
                                    onError = { error ->
                                        Log.d("API_CALL", "Error: $error")
                                    }
                                )
                            }
                        }
                    }
                },
                onError = { error ->
                    Log.d("API_CALL", "Error: $error")
                }
            )
        }
        is DataState.Failure->{

        }

        DataState.Empty -> TODO()
        is DataState.Success -> TODO()
        is DataState.SuccessAnswer -> TODO()
        is DataState.SuccessQuestion -> TODO()
        is DataState.SuccessSlot -> TODO()
        is DataState.SuccessSlots -> TODO()
        is DataState.SuccessFetchUser -> {

        }

        is DataState.SuccessJsonUser -> TODO()
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
                    imageVector = ImageVector.vectorResource(id = R.drawable.logo_svg),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(converterHeight(120, context).dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier=Modifier.align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                TypewriterText(texts = listOf("Welcome to Agati"), Color.White)
            }
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
                        .size(converterHeight(120, context).dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        var eventId by remember {
            mutableStateOf("")
        }
        Box(modifier= Modifier
            .fillMaxWidth()
            .height(converterHeight(400, context).dp)
            .padding(converterHeight(10, context).dp)
        )
        {

            Column(modifier = Modifier.fillMaxWidth()){
                Spacer(modifier = Modifier.height(10.dp))
                slider()
            }

        }
        Column(modifier= Modifier
            .padding(converterHeight(10, context).dp)
            .height(converterHeight(200, context).dp)
            .clip(RoundedCornerShape(converterHeight(10, context).dp))
            .background(colorResource(id = R.color.lightorange))
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
            if (schemesArray == null) {
            } else {
                LazyColumn {
                    items(schemesArray!!.length()) { index ->
                        val scheme = schemesArray!!.getJSONObject(index)
                        val name = scheme.getString("name")
                        val link = scheme.getString("link")

                        SchemeCard(schemeName = name, link)
                    }
                }
            }

        }
        Column(
            modifier= Modifier
                .padding(converterHeight(10, context).dp)
                .clip(RoundedCornerShape(converterHeight(10, context).dp))
                .background(colorResource(id = R.color.lightblue))
        ) {
            Text(text = "Recommended Events",
                fontSize = converterHeight(20,context).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = converterHeight(5, context).dp),
                color=Color.White
            )
            if (recommendedEvents.length()>0) {
                eventId = recommendedEvents.getString(0)
            }
            else {
                eventId="-Njp7ySPE-z629UhUxVk"
            }
            val eventImage = getInfo("eventImage", eventId)
            val eventTag = getInfo("tag", eventId)
            val eventName = getInfo("eventName", eventId)
            val eventCost = getInfo("eventCost", eventId)
            EventCard(navigateToNextScreen = navigateToNextScreen,eventId=eventId,eventCost=eventCost, eventTag = eventTag, eventImage = eventImage, eventTitle = eventName)
            Button(
                onClick = {
                    navigateToNextScreen(Screen.RecommendedEvents.route+"/"+recommendedEvents.toString())
                },
                modifier=Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text("View More")
            }
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
                modifier=Modifier
                    .fillMaxWidth()
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
    Box(modifier = Modifier
        .fillMaxSize()
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
