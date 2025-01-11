package com.example.empoweher.screen.Details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.composables.Exoplayer
import com.example.empoweher.model.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


@Composable
fun DetailsDp(navigateToNextScreen: (route: String)->Unit){

    val uri = Uri.parse("android.resource://com.example.empoweher/drawable/alert")

    var selectedImage by remember { mutableStateOf<Uri?>(uri) }

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
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Profile Picture",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.font1)),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black)

        )
        Spacer(modifier = Modifier.height(40.dp))

        val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    selectedImage = uri
                }
        val painter = rememberAsyncImagePainter(selectedImage)
        Column(
                modifier=Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(
                    painter = painter,
                    contentDescription = "Hello",
                    modifier = Modifier
                        .height(400.dp)
                        .width(400.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                    launcher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.pale_brown
                        )
                    )

                ) {
                    Text(
                        text = "Change Picture",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)

                    )
                }

        }

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
                val storage= FirebaseStorage.getInstance()
                val ref= storage.getReference()
                    .child(currentFirebaseUser +"/"+"Profile Picture")
                ref.putFile(selectedImage!!).addOnSuccessListener {
                    ref.getDownloadUrl().addOnSuccessListener { it
                        dbref.child(currentFirebaseUser).child("Dp").setValue(it.toString())
                    }
                }
                navigateToNextScreen(Screen.Home.route)

            }) {

            Text(
                text = "Get Started",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font1)),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white)

            )

        }
    }
}