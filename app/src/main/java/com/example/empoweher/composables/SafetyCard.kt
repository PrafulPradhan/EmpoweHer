package com.example.empoweher.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.empoweher.R
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.screen.Details.converterWidth

@Composable
fun SafetyCard(navigateToNextScreen: (route: String)->Unit, route: String, uristring: String, title:String,description:String,color: Color,routed:()->Unit?,boolean: Boolean){
    val context= LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(converterHeight(150, context).dp)
            .padding(converterHeight(15, context).dp)
            .clickable {
                if (boolean == false) {
                    navigateToNextScreen(route)
                } else {
                    routed()
                }
            },
        colors = CardDefaults.cardColors(containerColor = color),
    ) {
        Column(modifier=Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Spacer(modifier = Modifier.width(converterWidth(10, LocalContext.current).dp))
                Column(modifier=Modifier.fillMaxWidth(.63f)
                    .wrapContentSize())
                {
                    SampleText(text = title, 24, textColor = colorResource(id = R.color.white))
                    SampleText(text = description, 16, textColor = colorResource(id = R.color.white))
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .size(converterHeight(150,context).dp)
                    .padding(start = converterHeight(5, context).dp)
                    .offset(x = converterHeight(15, context).dp, y = 0.dp)
                     ) {
                    Exoplayer(uri = Uri.parse(uristring))
                }
            }
        }
    }
}