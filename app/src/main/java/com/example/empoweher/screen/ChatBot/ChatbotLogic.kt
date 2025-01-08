package com.example.empoweher.screen.ChatBot

import com.example.empoweher.model.Screen

class ChatbotLogic {
    private var currentSection = "home"

    private val sections = mapOf(
        "home" to "Welcome! Navigate to: Home, Safety, Events, OpenForum",
        "safety" to "Profile section. Edit your profile. Type 'back' to return to Home.",
        "events" to "Settings section. Adjust preferences. Type 'back' to return to Home.",
        "openforum" to "Help section. Find support. Type 'back' to return to Home."
    )

    fun getMessage(): String {
        return sections[currentSection] ?: "Section not found."
    }

    fun handleCommand(command: String, navigateToNextScreen: (route: String) -> Unit) {
        when (command) {
            "home" -> {
                navigateToNextScreen(Screen.Home.route)
            }
            "safety" ->{
                navigateToNextScreen(Screen.Safety.route)
            }
            "events" ->{
                navigateToNextScreen(Screen.Events.route)
            }
            "openforum" ->{
                navigateToNextScreen(Screen.Ask.route)
            }
            else -> {
                if (command.lowercase() == "back") {
                    currentSection = "home"
//                    getMessage()
                } else {
                    "Invalid command. Type 'back' to return to Home."
                }
            }
        }
    }
}