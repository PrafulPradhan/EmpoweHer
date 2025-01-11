package com.example.empoweher.auth.signin

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.screen.Details.converterHeight
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    navigateToHome: () -> Unit,
//    navigateToDetails:()->Unit,
) {
//    var alreadySignedIn by remember {
//        mutableStateOf(false)
//    }
//    val currentFirebaseUser=FirebaseAuth.getInstance().currentUser
//    Log.d("Cure")
//    if (currentFirebaseUser!=null){
//        alreadySignedIn=true
//    }
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
//            if (alreadySignedIn) {
//                navigateToDetails()
//            }
//            else{
                navigateToHome()
//            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff7e7d7)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(40.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_svg),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp),
            contentScale = ContentScale.Crop
        )
//        )
        Image(
            painter = painterResource(id = R.drawable.women_login),
            contentDescription = "Login Pic",
            modifier = Modifier
                .size(390.dp, 300.dp),
            contentScale = ContentScale.Fit
        )

        TypewriterText(

            texts = listOf(
                "Get Connected For Better Experience"
            ),
        )

        Image(
            painter = painterResource(id = R.drawable.google_signin),
            contentDescription = "Google",
            modifier = Modifier
                .height(70.dp)
                .width(250.dp)
                .clickable {
                    onSignInClick()
                },
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(5.dp))

        Column {
            Text(text = "About Us",
                fontSize= 20.sp,
                modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center,
                fontWeight=FontWeight.Bold,
                color = Color(R.color.black),
                fontStyle = FontStyle(R.font.font1),
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {
                Image(
                    painter = painterResource(id = R.drawable.insta),
                    contentDescription = "insta",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/")
                            ).setPackage("com.instagram.android")
                            context.startActivity(intent)
                        },
                    contentScale = ContentScale.Fit


                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "facebook",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "twitter",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Fit
                )
            }

        }

    }

}


@Composable
fun TypewriterText(
    texts: List<String>,
    color: Color=Color.Black
) {
    var textIndex by remember {
        mutableStateOf(0)
    }
    var textToDisplay by remember {
        mutableStateOf("")
    }
    val context= LocalContext.current
    LaunchedEffect(
        key1 = texts,
    ) {
        while (textIndex < texts.size) {
            texts[textIndex].forEachIndexed { charIndex, _ ->
                textToDisplay = texts[textIndex]
                    .substring(
                        startIndex = 0,
                        endIndex = charIndex + 1,
                    )
                delay(110)
            }
            textIndex = (textIndex + 1) % texts.size
            delay(3000)

        }
    }

    Text(
        text = textToDisplay,
        fontSize = converterHeight(24, context).sp,
        fontWeight = FontWeight.Bold,
        modifier=Modifier.padding(start=converterHeight(48,context).dp,top=converterHeight(15,context).dp),
        fontFamily = FontFamily(Font(R.font.font1)),
        color=color
    )
}