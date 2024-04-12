package com.example.empoweher.composables

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.activities.Payment
import com.example.empoweher.model.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailedEventCard(eventId:String?="",navigateToNextScreen: (route: String)->Unit) {


    var currentUser="24Si2cNeD8Uq7vIbGCTDUSAHNOg1"
    var currentFirebaseUser:String?=""
    try {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid

    }
    catch (e:Exception){

    }

    val list = remember { mutableStateListOf<String>()}

    LaunchedEffect(key1 = Unit) {
        FirebaseDatabase.getInstance().getReference("Users/24Si2cNeD8Uq7vIbGCTDUSAHNOg1/bookedEvents").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val e = data.getValue(String::class.java)
                    Log.d("jwsh",e.toString())
                    list.add(e!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


    var booked by remember {
        mutableStateOf(false)
    }

    if (list.contains(eventId)){
        booked=true
    }


    if(currentFirebaseUser != ""&& currentFirebaseUser!=null){
        currentUser = currentFirebaseUser;
    }

    var context= LocalContext.current
    var eventTitle by remember {
        mutableStateOf("")
    }
    var eventDescription by remember {
        mutableStateOf("")
    }
    var eventAddress by remember {
        mutableStateOf("")
    }
    var eventTag by remember {
        mutableStateOf("")
    }
    var eventStartDate by remember {
        mutableStateOf("")
    }
    var eventEndDate by remember {
        mutableStateOf("")
    }
    var timing by remember {
        mutableStateOf("")
    }
    var duration by remember {
        mutableStateOf("")
    }
    var capacity by remember {
        mutableStateOf("")
    }
    var contact by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var eventCost by remember {
        mutableStateOf("")
    }
    var eventImage by remember {
        mutableStateOf("")
    }
    var vacancy by remember {
        mutableStateOf("")
    }
    eventTitle= getInfo("eventName", eventId)
    eventAddress= getInfo("address", eventId)
    eventDescription= getInfo("description", eventId)
    eventTag= getInfo("tag", eventId)
    duration= getInfo("duration", eventId)
    eventStartDate= getInfo("startDate", eventId)
    eventEndDate= getInfo("endDate", eventId)
    city= getInfo("city", eventId)
    eventCost  = getInfo("eventCost", eventId)
    eventImage= getInfo("eventImage", eventId)
    timing= getInfo("timing",eventId)
    capacity= getInfo("capacity",eventId)
    contact=getInfo("contact",eventId)
    vacancy=getInfo("vacancy",eventId)
    var painter= rememberAsyncImagePainter(model = eventImage)
    var scroll =rememberScrollState()
    Column(modifier=Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .background(colorResource(id = R.color.cream))
                .verticalScroll(scroll)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.lightorange)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Text(
                    eventTitle, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.font1)
                    ),
                    fontWeight = FontWeight.Bold, fontSize = 30.sp
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Box(modifier = Modifier.height(300.dp)) {
                    Image(
                        painter = painter,
                        contentDescription = "cd",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black

                                ),
                                startY = 300f
                            )
                        )
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.emeraldgreen)
                ),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = eventDescription, modifier = Modifier.padding(5.dp, 5.dp),
                        fontFamily = FontFamily(
                            Font(R.font.font1)
                        ),
                        fontSize = 16.sp
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.emeraldgreen)
                ),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Event Details", modifier = Modifier
                            .padding(5.dp, 5.dp)
                            .fillMaxWidth(),
                        fontFamily = FontFamily(
                            Font(R.font.font1)
                        ),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    PrintText(text = "Address : $eventAddress")
                    PrintText(text = "City : $city")
                    PrintText(text = "Phone No.: +91$contact")
                    PrintText(text = "Tag : $eventTag")
                    PrintText(text = "Dates : $eventStartDate to $eventEndDate")
                    PrintText(text = "Timing : $timing")
                    PrintText(text = "Duration in Hours : $duration")
                    PrintText(text = "Capacity : $capacity")
                }
            }
        }
        Text(text = "Seats Left : $vacancy", modifier = Modifier
            .background(colorResource(id = R.color.teal_200))
            .fillMaxWidth(),
            fontFamily = FontFamily(
                Font(R.font.font1)
            ),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp)
        Button(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            shape = RoundedCornerShape(0),
            onClick = {

                if (Integer.parseInt(vacancy)>0 && !booked){
                    Toast.makeText(context,"Enrolled",Toast.LENGTH_SHORT).show()
                    var vacancyUpdated=Integer.parseInt(vacancy)-1
                    val dbref = FirebaseDatabase.getInstance().getReference("Event");
                    dbref.child(eventId!!).child("vacancy").setValue(vacancyUpdated.toString())
                    val Users = FirebaseDatabase.getInstance().getReference("Users");

                    Users.child(currentUser).child("bookedEvents").child(eventId).setValue(eventId).addOnSuccessListener {
                        booked=true
                        val intent = Intent(context, Payment::class.java)
                        intent.putExtra("eventId", eventId)
                        context.startActivity(intent)



                    }


                }

                else{
                    if (booked){
                        Toast.makeText(context,"Already Booked",Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(context,"No Seats Left",Toast.LENGTH_SHORT).show()
                    }
                }
        }) {

            Text(text = "Enroll Now", fontSize = 18.sp)
        }
    }
}


@Composable
fun getInfo(thing:String?,eventId: String?): String {
    val dbref = FirebaseDatabase.getInstance().getReference();
    val event=dbref.child("Event").child(eventId!!)
    var eventValue by remember {
        mutableStateOf("")
    }
    event.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            eventValue=snapshot.child(thing!!).getValue(String::class.java).toString();
        }
        override fun onCancelled(error: DatabaseError) {

        }
    })
    return eventValue
}

@Composable
fun PrintText(text:String){
    Text(text = text,modifier=Modifier.padding(5.dp,5.dp),
        fontFamily = FontFamily(
            Font(R.font.font1)
        ),
        fontSize = 16.sp)
}