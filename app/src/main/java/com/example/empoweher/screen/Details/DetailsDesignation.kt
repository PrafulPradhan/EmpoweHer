package com.example.empoweher.screen.Details

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.composables.Exoplayer
import com.example.empoweher.model.Screen
import com.example.empoweher.model.Slot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val calendar = Calendar.getInstance()
val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

val currentDate = dateFormat.format(calendar.time)
val dayOfWeek = dayFormat.format(calendar.time)

@Composable
fun DetailsDesignation(navigateToNextScreen: (route: String)->Unit){

    val context= LocalContext.current
    val scrollState = rememberScrollState()
    var designation by remember{
        mutableStateOf("")
    }

    var bio by remember{
        mutableStateOf("")
    }

    var checked by remember{
        mutableStateOf("false")
    }

    var expertise by remember{
        mutableStateOf("")
    }

    var fees by remember{
        mutableStateOf("")
    }

    var linkedInId by remember{
        mutableStateOf("")
    }

    var chwichar by remember{
        mutableStateOf("")
    }

    var exp by remember{
        mutableStateOf("")
    }

    var english by remember{
        mutableStateOf(false)
    }

    var hindi by remember{
        mutableStateOf(false)
    }
    var marathi by remember{
        mutableStateOf(false)
    }
    var gujarati by remember{
        mutableStateOf(false)
    }
    var sindhi by remember{
        mutableStateOf(false)
    }
    var tamil by remember{
        mutableStateOf(false)
    }
    var telugu by remember{
        mutableStateOf(false)
    }
    var punjabi by remember{
        mutableStateOf(false)
    }
    var french by remember{
        mutableStateOf(false)
    }
    var german by remember{
        mutableStateOf(false)
    }

    val weeks= mapOf("0" to "Sunday","1" to "Monday","2" to "Tuesday","3" to "Wednesday","4" to "Thursday","5" to "Friday","6" to "Saturday")

    val uri = Uri.parse("android.resource://com.example.empoweher/drawable/alert")
    var selectedImage by remember { mutableStateOf<Uri?>(uri) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri
        }

    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid

    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");

//    dbref.child(currentFirebaseUser).child("name").setValue("hello")

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cream))
            .padding(converterHeight(20, LocalContext.current).dp)
            .verticalScroll(scrollState)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_svg),
            contentDescription = "Logo",
            modifier = Modifier
                .size(converterHeight(80, LocalContext.current).dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(converterHeight(75, LocalContext.current).dp))
        Text(
            text = "Add Designation",
            fontSize = converterHeight(25, LocalContext.current).sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )
        OutlinedTextField(
            value = designation,
            label = { Text(text = "Designation") },
            textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, LocalContext.current).sp)),
            onValueChange = { str ->
                if(str.length<=100){
                    designation = str
                }
                else{
                    Toast.makeText(context,"Only 100 characters Allowed", Toast.LENGTH_SHORT).show()

                }

            },modifier= Modifier
                .padding(end = converterHeight(10, LocalContext.current).dp)
                .padding(top = converterHeight(10, LocalContext.current).dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(converterHeight(50, LocalContext.current).dp))

        Text(
            text = "Add Bio",
            fontSize = converterHeight(25, LocalContext.current).sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )
        OutlinedTextField(
            value = bio,
            label = { Text(text = "Short Description About Yourself") },
            textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
            onValueChange = { str ->
                if(str.length<=100){
                    bio = str
                }
                else{
                    Toast.makeText(context,"Only 1000 characters Allowed", Toast.LENGTH_SHORT).show()

                }

            },modifier= Modifier
                .padding(end = converterHeight(10, LocalContext.current).dp)
                .padding(top = converterHeight(10, LocalContext.current).dp)
                .fillMaxWidth()
                .height(converterHeight(300, LocalContext.current).dp)
        )

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                "Are you an entrepreneur ?"
            )
            Checkbox(
                checked = checked.toBoolean(),
                onCheckedChange = {
                    checked = it.toString()
                }
            )
        }



        if(checked == "true"){
            Text(text="Enter your details to get verified")
            OutlinedTextField(
                value = expertise,
                label = { Text(text = "Enter your domain") },
                textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
                onValueChange = { str ->
                    expertise = str
                },modifier= Modifier
                    .padding(end = converterHeight(10, LocalContext.current).dp)
                    .padding(top = converterHeight(10, LocalContext.current).dp)
                    .fillMaxWidth()
//                    .height(converterHeight(300, LocalContext.current).dp)
            )

            OutlinedTextField(
                value = fees,
                label = { Text(text = "Enter your fees") },
                textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
                onValueChange = { str ->
                    fees = str
                },modifier= Modifier
                    .padding(end = converterHeight(10, LocalContext.current).dp)
                    .padding(top = converterHeight(10, LocalContext.current).dp)
                    .fillMaxWidth()
//                    .height(converterHeight(300, LocalContext.current).dp)
            )
            val painter = rememberAsyncImagePainter(selectedImage)
            Image(
                painter = painter,
                contentDescription = "Hello",
                modifier = Modifier
                    .height(400.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )

            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pale_brown
                    )
                ),
                modifier = Modifier.padding(top=10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Upload Certificate",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.font1)),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)

                )
            }

            OutlinedTextField(
                value = exp,
                label = { Text(text = "Enter number of years of experience") },
                textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
                onValueChange = { str ->
                    exp = str
                },modifier= Modifier
                    .padding(end = converterHeight(10, LocalContext.current).dp)
                    .padding(top = converterHeight(10, LocalContext.current).dp)
                    .fillMaxWidth()
//                    .height(converterHeight(300, LocalContext.current).dp)
            )

            OutlinedTextField(
                value = linkedInId,
                label = { Text(text = "Enter your LinkedIn profile link") },
                textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
                onValueChange = { str ->
                    linkedInId = str
                },modifier= Modifier
                    .padding(end = converterHeight(10, LocalContext.current).dp)
                    .padding(top = converterHeight(10, LocalContext.current).dp)
                    .fillMaxWidth()
//                    .height(converterHeight(300, LocalContext.current).dp)
            )

            OutlinedTextField(
                value = chwichar,
                label = { Text(text = "Enter your Twitter profile link") },
                textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
                onValueChange = { str ->
                    chwichar = str
                },modifier= Modifier
                    .padding(end = converterHeight(10, LocalContext.current).dp)
                    .padding(top = converterHeight(10, LocalContext.current).dp)
                    .fillMaxWidth()
//                    .height(converterHeight(300, LocalContext.current).dp)
            )

            Text(text="Select Known Languages")

            InterestCheckBox(title = "English", value = english, onValueChange = {english=it})
            InterestCheckBox(title = "Hindi", value = hindi, onValueChange = {hindi=it})
            InterestCheckBox(title = "Marathi", value = marathi, onValueChange = {marathi=it})
            InterestCheckBox(title = "Gujarati", value = gujarati, onValueChange = {gujarati=it})
            InterestCheckBox(title = "Sindhi", value = sindhi, onValueChange = {sindhi=it})
            InterestCheckBox(title = "Tamil", value = tamil, onValueChange = {tamil=it})
            InterestCheckBox(title = "Telugu", value = telugu, onValueChange = {telugu=it})
            InterestCheckBox(title = "Punjabi", value = punjabi, onValueChange = {punjabi=it})
            InterestCheckBox(title = "French", value = french, onValueChange = {french=it})
            InterestCheckBox(title = "German", value = german, onValueChange = {german=it})
        }

        Spacer(modifier = Modifier.height(converterHeight(60, LocalContext.current).dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(.75f)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.pale_brown
                )
            )
            ,
            onClick = {
                dbref.child(currentFirebaseUser).child("designation").setValue(designation)
                dbref.child(currentFirebaseUser).child("bio").setValue(bio)
                dbref.child(currentFirebaseUser).child("isEnt").setValue(checked)
                if(checked == "true"){
                    val list = mutableListOf<String>()

                    if(english){
                        list.add("English")
                    }

                    if(hindi){
                        list.add("Hindi")
                    }

                    if(marathi){
                        list.add("Marathi")
                    }

                    if(sindhi){
                        list.add("Sindhi")
                    }

                    if(gujarati){
                        list.add("Gujarati")
                    }

                    if(tamil){
                        list.add("Tamil")
                    }

                    if(telugu){
                        list.add("Telugu")
                    }

                    if(punjabi){
                        list.add("Punjabi")
                    }

                    if(french){
                        list.add("French")
                    }

                    if(german){
                        list.add("German")
                    }
                    dbref.child(currentFirebaseUser).child("domain").setValue(expertise)
                    dbref.child(currentFirebaseUser).child("fees").setValue(fees)
                    dbref.child(currentFirebaseUser).child("experience").setValue(exp)
                    dbref.child(currentFirebaseUser).child("linkedinid").setValue(linkedInId)
                    dbref.child(currentFirebaseUser).child("twitterid").setValue(chwichar)
                    dbref.child(currentFirebaseUser).child("languages").setValue(list)
                    val storage= FirebaseStorage.getInstance()
                    val ref= storage.getReference()
                        .child(currentFirebaseUser +"/"+"certificate")
                    ref.putFile(selectedImage!!).addOnSuccessListener {
                        ref.getDownloadUrl().addOnSuccessListener { it
                            dbref.child(currentFirebaseUser).child("certificate").setValue(it.toString())
                        }
                    }
                    dbref.child(currentFirebaseUser).child("Schedule").setValue(weeks)
                    val slots= listOf("9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00")
                    val slotStart= listOf("9:00","10:00","11:00","12:00","13:00","14:00","16:00","17:00","18:00","19:00","20:00","21:00")
                    val slotEnd= listOf("10:00","11:00","12:00","13:00","14:00","15:00","17:00","18:00","19:00","20:00","21:00","22:00")

                    var index = 0
//                    val slot= Slot(currentFirebaseUser,null,status="undefined", start = start,end=end,key=key,day=day)
                    for(j in weeks.keys){
                        for(i in slots.indices){
                            val slot= Slot(currentFirebaseUser,null,status="undefined", start = slotStart[i],end=slotEnd[i],key=slots[i],day=weeks[j], index = index.toString())
                            dbref.child(currentFirebaseUser).child("Schedule").child(j).child(slots[i]).setValue(slot)
                            index++
                        }
                    }
                }
                if(checked.toBoolean()){
                    navigateToNextScreen(Screen.Scheduling.route)
                }else{
                    navigateToNextScreen(Screen.DetailsInterests.route)
                }

            }) {

            Text(
                text = "Continue",
                fontSize = converterHeight(25, LocalContext.current).sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white)

            )

        }
    }
}

@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp
) {

    var clickCount by remember {
        mutableStateOf(0)
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = converterHeight(32, LocalContext.current).dp, end = 32.dp),
        onClick = {
            clickCount++
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click $clickCount",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


