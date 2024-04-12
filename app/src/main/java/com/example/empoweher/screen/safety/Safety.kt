@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.empoweher.screen.safety

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.activities.LocationActivity
import com.example.empoweher.activities.Twitter
import com.example.empoweher.composables.SafetyCard
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight

@SuppressLint("SuspiciousIndentation")
@Preview()
@Composable
fun Safety(navigateToNextScreen: (route: String)->Unit) {
    val context= LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_450))

    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(converterHeight(10, context).dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.teal_700
            )),
            elevation = CardDefaults.cardElevation(converterHeight(20,context).dp),
            shape = RoundedCornerShape(converterHeight(25,context).dp)
        ){
            Spacer(modifier = Modifier.height(converterHeight(20, LocalContext.current).dp))
            Text(
                text = "SAFETY FEATURES",
                fontStyle = FontStyle(R.font.font1),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = converterHeight(35,context).sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontFamily = FontFamily(Font(R.font.font1)),
                color= Color.White
            )

        }
        Card(modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .fillMaxHeight(.90f)
            .padding(converterHeight(10, context).dp),
            shape = RoundedCornerShape(converterHeight(25,context).dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
            elevation = CardDefaults.cardElevation(converterHeight(20,context).dp))
        {
            Spacer(modifier = Modifier.height(converterHeight(15, LocalContext.current).dp))
            SafetyCard(
                navigateToNextScreen = navigateToNextScreen,
                route = Screen.FakeCall.route,
                uristring = "android.resource://com.example.empoweher/raw/safety_incomingcall_icon",
                title = "Fake Call",
                description="You Can Do the fake call with this",
                color= colorResource(id = R.color.yellow),
                routed = {},
                false,
            )
            SafetyCard(
                navigateToNextScreen = navigateToNextScreen,
                route = Screen.Alerts.route,
//                painter = painterResource(id = R.drawable.alert_new) ,
                uristring = "android.resource://com.example.empoweher/raw/safety_alert_icon",
                title = "Alert",
                description="You Can Send your Location to your emergency Contacts",
                color= colorResource(id = R.color.lightgreen),
                routed = {
//                    val navigate = Intent(context, LocationActivity::class.java)
//                    context.startActivity(navigate)
                    val navigate = Intent(context, LocationActivity::class.java)
                    context.startActivity(navigate)
                },
                true
            )
            SafetyCard(
                navigateToNextScreen = navigateToNextScreen,
                route = Screen.ContactOption.route,
//                painter = painterResource(id = R.drawable.emergency) ,
                uristring = "android.resource://com.example.empoweher/raw/safety_sos_icon",
                title = "Emergency",
                description="Add Emergency Contacts here",
                color= colorResource(id = R.color.lightblue),
                routed={},
                false
            )
            SafetyCard(
                navigateToNextScreen = navigateToNextScreen,
                route = Screen.FakeCall.route,
//                painter = painterResource(id = R.drawable.police_new) ,
                uristring = "android.resource://com.example.empoweher/raw/safety_policestn_icon",
                title = "Nearby Police Station",
                description="Get Location of nearest police station",
                color= colorResource(id = R.color.purple),
                routed={
                    val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/search/nearby+police+station/")
                        ).setPackage("com.google.android.apps.maps")
                        context.startActivity(intent)
                },
                true
            )
        }
    }
}





