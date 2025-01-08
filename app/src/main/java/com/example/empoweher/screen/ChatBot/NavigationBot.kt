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

//@Composable
//fun ChatbotApp() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") { HomeScreen(navController) }
//        composable("personal_productivity") { /* Add Screen Content Here */ }
//        composable("health_wellness") { /* Add Screen Content Here */ }
//        composable("finance_budgeting") { /* Add Screen Content Here */ }
//        composable("education_learning") { /* Add Screen Content Here */ }
//    }
//}

@Composable
fun NavigationBot(navigateToNextScreen: (route: String) -> Unit) {
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
            buttons = listOf("Time management", "Task prioritization"),
            navigateToNextScreen,
            route = Screen.Home.route
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Health and Wellness",
            buttons = listOf("Nutrition", "Fitness", "Mental health"),
            navigateToNextScreen,
            route = Screen.Safety.route
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Finance and Budgeting",
            buttons = listOf("Money management", "Budgeting", "Investing"),
            navigateToNextScreen,
            route = Screen.Events.route
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(
            title = "Education and Learning",
            buttons = listOf("Learning new skills", "Finding educational resources"),
            navigateToNextScreen,
            route = Screen.Ask.route
        )
    }
}

@Composable
fun CategorySection(
    title: String,
    buttons: List<String>,
    navigateToNextScreen: (route: String) -> Unit,
    route: String
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
                    onClick = { navigateToNextScreen(route) },
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
