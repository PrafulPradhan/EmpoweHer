package com.example.empoweher.screen.Details

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.empoweher.R
import com.example.empoweher.composables.Exoplayer
import com.example.empoweher.model.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Composable
fun DetailsDesignation(navigateToNextScreen: (route: String)->Unit){

    val context= LocalContext.current

    var designation by remember{
        mutableStateOf("")
    }

    var bio by remember{
        mutableStateOf("")
    }

    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser.toString()

    val dbref = FirebaseDatabase.getInstance()
        .getReference("Users");

//    dbref.child(currentFirebaseUser).child("name").setValue("hello")

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cream))
            .padding(20.dp)

    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_svg),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(75.dp))
        Text(
            text = "Add Designation",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )
        OutlinedTextField(
            value = designation,
            label = { Text(text = "Designation") },
            textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 20.sp)),
            onValueChange = { str ->
                if(str.length<=100){
                    designation = str
                }
                else{
                    Toast.makeText(context,"Only 100 characters Allowed", Toast.LENGTH_SHORT).show()

                }

            },modifier= Modifier
                .padding(end = 10.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Add Bio",
            fontSize = 25.sp,
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
                .padding(end = 10.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

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
                dbref.child("Aman Hande").child("designation").setValue(designation)
                dbref.child("Aman Hande").child("bio").setValue(bio)
                navigateToNextScreen(Screen.DetailsInterests.route)


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
            .padding(start = 32.dp, end = 32.dp),
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


