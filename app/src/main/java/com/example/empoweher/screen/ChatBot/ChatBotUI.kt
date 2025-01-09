package com.example.empoweher.screen.ChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empoweher.model.Screen
val routes = mapOf(
    "Fake Call" to Screen.FakeCall.route,
    "Emergency Contact" to Screen.ContactOption.route,
    "Events" to Screen.Events.route,
    "Open Forum" to Screen.Ask.route,
    "Home" to Screen.Home.route,
    "Event Form" to Screen.EventForm.route,
)

@Composable
fun ChatbotUI(navigateToNextScreen: (route: String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Please select your choice.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Buttons Layout
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                ChatbotButton(text = "Home", navigateToNextScreen)
                ChatbotButton(text = "Fake Call", navigateToNextScreen)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ){
                ChatbotButton(text = "Emergency Contact", navigateToNextScreen)
                ChatbotButton(text = "Events", navigateToNextScreen)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ){
                ChatbotButton(text = "Open Forum", navigateToNextScreen)
                ChatbotButton(text = "Event Form", navigateToNextScreen)
            }
        }
    }
}

@Composable
fun ChatbotButton(text: String,navigateToNextScreen: (route: String) -> Unit) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC)),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            onClick = {
                navigateToNextScreen(routes[text]!!)
            }
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
