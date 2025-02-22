package com.example.empoweher.composables

import android.nfc.Tag
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.converterHeight


@Composable
fun EventCard(navigateToNextScreen: (route: String) -> Unit,
              eventId:String?="",
              eventTitle: String?="",
              eventCity:String?="",
              eventCapacity:String?="",
              eventStartDate:String?="",
              eventEndDate:String?="",
              eventTiming:String?="",
              eventCost:String?="",
              eventImage:String?="",
              eventTag: String?="",
              eventStatus:String?="ongoing"
){

    var color = colorResource(id = R.color.mauve)

    if(eventCost!!.isNotEmpty()){
    if(Integer.parseInt(eventCost!!)==1) {
        color = colorResource(id = R.color.emeraldgreen)
    }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp)
            .clickable {
                navigateToNextScreen(Screen.DetailedEventCard.route + "/" + eventId!!)
            },
        colors = CardDefaults.cardColors(containerColor = color,
    )
    ){
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .fillMaxHeight()
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val painter= rememberAsyncImagePainter(model = eventImage)
                    Image(painter = painter,
                        contentDescription = "EventImage",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp)))
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight()) {
                Spacer(modifier = Modifier.height(5.dp))
                SampleText(eventTitle!!,16)
                SampleText("Tag : "+eventTag!!)
                SampleText("Location : "+eventCity!!)
                SampleText("Cost : "+eventCost!!+" Rs")
            }
        }
    }
}

@Composable
fun SampleText(text: String,fontSize:Int?=12, textColor: Color = colorResource(id = R.color.black)){
    Text(text = text,
        color = textColor,
        fontFamily = FontFamily(Font(R.font.font1)),
        fontSize = converterHeight(fontSize!!, LocalContext.current).sp,
        modifier = Modifier
            .padding(converterHeight(5, LocalContext.current).dp))
}