package com.example.empoweher.screen.profile

import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.activities.VideoConferencing
import com.example.empoweher.composables.SampleText
import com.example.empoweher.composables.getChildCount
import com.example.empoweher.composables.getInfoUser
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Screen
import com.example.empoweher.model.User
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.viewmodel.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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
    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser?.uid!!
    val isEnt = getInfoUser(thing = "isEnt", userId = userId)
    var color = colorResource(R.color.lightblue)

    if(isEnt != null && isEnt == "true"){
        color = colorResource(R.color.emeraldgreen)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cream))

    ) {
//        Icon(imageVector = Icons.Rounded.Person, contentDescription = "Account",
//            modifier = Modifier.size(50.dp))

        Spacer(modifier = Modifier.height(converterHeight(10, context).dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = "ProfilePic",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(converterHeight(100, context).dp)
                    .border(
                        BorderStroke(converterHeight(3, context).dp, color),
                        CircleShape
                    )
            )
        }
        Icon(imageVector = Icons.Outlined.Add, contentDescription = "Change",
            modifier = Modifier.fillMaxWidth()
                .offset(converterHeight(35, context).dp,-converterHeight(17, context).dp))

        Text(text=name,
            textAlign = TextAlign.Center,
            fontSize = converterHeight(20, context).sp,
            modifier = Modifier.fillMaxWidth(),
            fontStyle = FontStyle(R.font.font1)
        )

        Spacer(modifier = Modifier.height(converterHeight(10, context).dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(modifier = Modifier.size(converterHeight(70, context).dp)){
                Text(text=following.toString(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text="Following", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
            Column(modifier = Modifier.size(converterHeight(70, context).dp)){
                Text(text=followers.toString(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text="Followers", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
            Column(modifier = Modifier.size(converterHeight(70, context).dp)){
                Text(text="0", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text="Events", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }

        if(userId != currentFirebaseUser){
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {
                    if(userId != currentFirebaseUser){
                        if (userId != null) {
                            val dbref = FirebaseDatabase.getInstance().getReference("Users")
                            dbref.child(userId).child("followers").child(currentFirebaseUser).setValue(currentFirebaseUser)
                            dbref.child(currentFirebaseUser).child("following").child(userId).setValue(userId)
                        }
                    }
                }) { Text("Follow") }
                Button(onClick = {}) { Text("Message") }
            }
        }

        Spacer(modifier = Modifier.height(converterHeight(20, context).dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()  
                .offset(y=-converterHeight(15, context).dp)
                .padding(start = converterHeight(20, context).dp, end = converterHeight(20, context).dp, bottom = converterHeight(5, context).dp)
                .clip(RoundedCornerShape(converterHeight(10, context).dp))
                .shadow(ambientColor = Color.Blue, elevation = converterHeight(30, context).dp),
            elevation = CardDefaults.cardElevation(converterHeight(20, context).dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.emeraldgreen))
        ) {
            Column(modifier = Modifier.padding()){
                Spacer(modifier = Modifier.height(converterHeight(10, context).dp))
                Spacer(modifier = Modifier.height(converterHeight(5, context).dp))
                SampleText(text = name, fontSize = converterHeight(24, context), textColor = colorResource(id = R.color.white))
                SampleText(text = designation, fontSize = converterHeight(24, context), textColor = colorResource(id = R.color.white))
                SampleText(text = bio, fontSize = converterHeight(24, context), textColor = colorResource(id = R.color.white))
            }
        }

        Spacer(modifier = Modifier.height(converterHeight(20, context).dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y=-converterHeight(15, context).dp)
                .padding(start = converterHeight(20, context).dp, end = converterHeight(20, context).dp, bottom = converterHeight(5, context).dp)
                .clip(RoundedCornerShape(converterHeight(10, context).dp))
                .clickable{
                    navigateToNextScreen(Screen.Timings.route)
                }
                .shadow(ambientColor = Color.Blue, elevation = converterHeight(30, context).dp),
            elevation = CardDefaults.cardElevation(converterHeight(20, context).dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightorange))

        ) {
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(converterHeight(20, context).dp),
               horizontalArrangement = Arrangement.SpaceEvenly,
               verticalAlignment = Alignment.CenterVertically
           ) {

               Image(
                   painter = painterResource(id = R.drawable.schedule),
                   contentDescription = "addContact",
                   modifier= Modifier
                       .padding(bottom=20.dp)
                       .clip(RoundedCornerShape(converterHeight(20,context).dp))
                       .size(converterHeight(100,context).dp),
                   contentScale = ContentScale.FillBounds,
               )
               Spacer(modifier = Modifier.width(converterHeight(25, context).dp))

               SampleText(text = "Maintain Your Schedule",fontSize = converterHeight(25, context))
           }
        }

        Button(
            onClick = {
                         FirebaseAuth.getInstance().signOut()
                         navigateToNextScreen(Screen.Login.route)
                      },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            Text(text = "Logout")
            //
        }
}
}