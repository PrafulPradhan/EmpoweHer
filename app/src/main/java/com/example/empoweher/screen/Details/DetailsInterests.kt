package com.example.empoweher.screen.Details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import com.example.empoweher.R
import com.example.empoweher.model.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Composable
fun DetailsInterests(navigateToNextScreen: (route: String)->Unit){

    var education by remember {
        mutableStateOf(false)
    }

    var safety by remember {
        mutableStateOf(false)
    }

    var socialAffairs by remember {
        mutableStateOf(false)
    }

    var dailyGuidance by remember {
        mutableStateOf(false)
    }

    var technical by remember {
        mutableStateOf(false)
    }

    var health by remember {
        mutableStateOf(false)
    }

    var astrology by remember {
        mutableStateOf(false)
    }

    var spiritual by remember {
        mutableStateOf(false)
    }

    var childProblems by remember {
        mutableStateOf(false)
    }

    var empowerment by remember {
        mutableStateOf(false)
    }

    var history by remember {
        mutableStateOf(false)
    }

    var arts by remember {
        mutableStateOf(false)
    }
    var careerGuidance by remember {
        mutableStateOf(false)
    }


    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid

    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");

//    dbref.child(currentFirebaseUser).child("name").setValue("hello")
//    dbref.child("Pokemon").child("name").setValue("hello")

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cream))
            .padding(20.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_svg),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "You're Interested In?",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier=Modifier
                .fillMaxWidth()
                .height(630.dp)
                .border(BorderStroke(2.dp, Color.Black))
        ) {
            InterestCheckBox(title = "Education", value = education, onValueChange = {education=it})
            InterestCheckBox(title = "Safety", value = safety, onValueChange = {safety=it})
            InterestCheckBox(title = "Empowerment", value = empowerment, onValueChange = {empowerment=it})
            InterestCheckBox(title = "Daily Guidance", value = dailyGuidance, onValueChange = {dailyGuidance=it})
            InterestCheckBox(title = "Arts", value = arts, onValueChange = {arts=it})
            InterestCheckBox(title = "Technical", value = technical, onValueChange = {technical=it})
            InterestCheckBox(title = "Social Affairs", value = socialAffairs, onValueChange = {socialAffairs=it})
            InterestCheckBox(title = "Child Problems", value = childProblems, onValueChange = {childProblems=it})
            InterestCheckBox(title = "Astrology", value = astrology, onValueChange = {astrology=it})
            InterestCheckBox(title = "Health", value = health, onValueChange = {health=it})
            InterestCheckBox(title = "Spiritual", value = spiritual, onValueChange = {spiritual=it})
            InterestCheckBox(title = "History", value = history, onValueChange = {history=it})
            InterestCheckBox(title = "Career Guidance", value = careerGuidance, onValueChange = {careerGuidance=it})
        }

        Spacer(modifier = Modifier.height(20.dp))

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

                val list= mutableListOf<String>()
                if (education){
                    list+="Education"
                }
                if (socialAffairs){
                    list.add("Social Affairs")
                }
                if (safety){
                    list.add("Education")
                }
                if (dailyGuidance){
                    list.add("Daily Guidance")
                }
                if (technical){
                    list.add("Technical")
                }
                if (health){
                    list.add("Health")
                }
                if (astrology){
                    list.add("Astrology")
                }
                if (spiritual){
                    list.add("Spiritual")
                }
                if (childProblems){
                    list.add("Child Problems")
                }
                if (empowerment){
                    list.add("Empowerment")
                }
                if (history){
                    list.add("History")
                }
                if (arts){
                    list.add("Arts")
                }
                if (careerGuidance){
                    list.add("Career Guidance")
                }

                dbref.child(currentFirebaseUser).child("interests").setValue(list)
                navigateToNextScreen(Screen.DetailsDp.route)

            }) {

            Text(
                text = "Continue",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white)

            )

        }
    }
}

@Composable
fun InterestCheckBox(
    title:String,
    value:Boolean,
    onValueChange:(Boolean)->Unit
){
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = value,
            modifier = Modifier.padding(end=10.dp),
            onCheckedChange = onValueChange,
        )
        Text(
            text = title,
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )

    }


}

