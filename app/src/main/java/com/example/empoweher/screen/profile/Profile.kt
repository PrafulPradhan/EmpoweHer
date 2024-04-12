package com.example.empoweher.screen.profile

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.activities.VideoConferencing
import com.example.empoweher.composables.SampleText
import com.example.empoweher.composables.getChildCount
import com.example.empoweher.composables.getInfoUser
import com.example.empoweher.model.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Profile(userId : String?=null,navigateToNextScreen: (route: String)->Unit) {
    val name = getInfoUser(thing = "name", userId = userId)
    val designation = getInfoUser(thing = "designation", userId = userId)
    val bio = getInfoUser(thing = "bio", userId = userId)
    val dp = getInfoUser(thing = "Dp", userId = userId)
    val image = rememberAsyncImagePainter(model = dp)
    val followers=getChildCount(path = "/Users/$userId/followers")
    val following=getChildCount(path = "/Users/$userId/following")
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
//        Icon(imageVector = Icons.Rounded.Person, contentDescription = "Account",
//            modifier = Modifier.size(50.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = "ProfilePic",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp)
                    .border(
                        BorderStroke(3.dp, colorResource(id = R.color.lightblue)),
                        CircleShape
                    )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "User Details", fontSize = 24.sp, color = colorResource(id = R.color.black),
            modifier = Modifier.padding(start = 30.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .shadow(ambientColor = Color.Blue, elevation = 30.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightblue))
        ) {
            Column(modifier = Modifier.padding()){
                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.height(5.dp))
                SampleText(text = name, fontSize = 24, textColor = colorResource(id = R.color.white))
                SampleText(text = designation, fontSize = 24, textColor = colorResource(id = R.color.white))
                SampleText(text = bio, fontSize = 24, textColor = colorResource(id = R.color.white))
                SampleText(text = "Interested Subjects: ", fontSize = 24, textColor = colorResource(id = R.color.white))
            }
        }

//        var slide = listOf<>()

        Button(onClick = {
            val navigate = Intent(context, VideoConferencing::class.java)
            context.startActivity(navigate)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Create or Join a Meet",)
        }
//
//        val videoConferencing = VideoConferencing()
//        videoConferencing.Video(context)

        Button(
            onClick = {
                         FirebaseAuth.getInstance().signOut()
                         navigateToNextScreen(Screen.Login.route)
                      },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            Text(text = "Logout")
        }
}
}