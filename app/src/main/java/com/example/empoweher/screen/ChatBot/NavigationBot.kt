package com.example.empoweher.screen.ChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.empoweher.model.Screen

@Composable
fun NavigationBot(navigateToNextScreen: (route: String) -> Unit) {
     val safety_routes = mapOf(
        "Fake Call" to Screen.FakeCall.route,
        "Emergency Contacts" to Screen.ContactOption.route
    )
    val home_routes = mapOf(
        "home" to Screen.Home.route
    )
    val event_routes = mapOf(
        "event" to Screen.Events.route
    )
    val ask_routes = mapOf(
        "ask" to Screen.Ask.route
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello,",
            fontSize = 36.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "how can I help you?",
            fontSize = 20.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))
        CategorySection(
            title = "Home",
            buttons = listOf("Home"),
            navigateToNextScreen,
            route = home_routes
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Safety",
            buttons = listOf("Fake Call", "Emergency Contacts"),
            navigateToNextScreen,
            route = safety_routes
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Events",
            buttons = listOf("Money management", "Budgeting", "Investing"),
            navigateToNextScreen,
            route = event_routes
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Open Forum",
            buttons = listOf("Learning new skills", "Finding educational resources"),
            navigateToNextScreen,
            route = ask_routes
        )
    }
}

@Composable
fun CategorySection(
    title: String,
    buttons: List<String>,
    navigateToNextScreen: (route: String) -> Unit,
    route: Map<String,String>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            buttons.forEach { buttonText ->
                Button(
                    onClick = {
                        val r=route[buttonText]
                        navigateToNextScreen(r!!) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = buttonText,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
