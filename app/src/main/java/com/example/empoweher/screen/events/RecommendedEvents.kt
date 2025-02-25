package com.example.empoweher.screen.events

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.R
import com.example.empoweher.composables.EventCard
import com.example.empoweher.composables.getInfo
import com.example.empoweher.composables.getInfoUser
import com.example.empoweher.model.Event
import org.json.JSONArray

@Composable
fun RecommendedEvents(navigateToNextScreen: (route: String)->Unit,jsonArray: JSONArray){
    val eventList= mutableListOf<Event>()

    for(i in 0 until jsonArray.length()){
        val eventId=jsonArray[i].toString()
        val eventTitle = getInfo("eventName",eventId)
        val eventCity=getInfo("city",eventId)
        val eventCapacity = getInfo("capacity",eventId)
        val eventStartDate =getInfo("startDate",eventId)
        val eventEndDate = getInfo("endDate",eventId)
        val eventTiming =getInfo("timing",eventId)
        val eventCost = getInfo("eventCost",eventId)
        val eventImage = getInfo("eventImage",eventId)
        val eventTag=getInfo("tag",eventId)
        val eventStatus=getInfo("status",eventId)
        val currentEvent=Event(eventId,eventTitle, city = eventCity, capacity = eventCapacity, startDate = eventStartDate, endDate = eventEndDate, timing = eventTiming, eventCost = eventCost, eventImage = eventImage, tag = eventTag, status = eventStatus
        )
        eventList.add(currentEvent)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.cream))
    ) {
        Text("Recommended Events",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(25.dp))
                .fillMaxHeight()
                .fillMaxWidth()
                .background(colorResource(R.color.cream))
        ) {
            items(eventList) { each ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .padding(5.dp)
                        .clickable {

                        },
                ) {

                    EventCard(
                        eventId = each.eventId,
                        eventTitle = each.eventName!!,
                        eventCity = each.city!!,
                        eventCapacity = each.capacity!!,
                        eventStartDate = each.startDate!!,
                        eventEndDate = each.endDate!!,
                        eventTiming = each.timing!!,
                        eventCost = each.eventCost!!,
                        eventImage = each.eventImage!!,
                        navigateToNextScreen = navigateToNextScreen,
                        eventTag = each.tag!!,
                        eventStatus = each.status
                    )
                }
            }
        }
    }
}