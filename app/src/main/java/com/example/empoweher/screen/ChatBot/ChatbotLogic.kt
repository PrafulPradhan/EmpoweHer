package com.example.empoweher.screen.ChatBot

class ChatbotLogic {
    private var currentSection = "home"

    private val sections = mapOf(
        "home" to "Welcome! Navigate to: Profile, Settings, Help, or Exit.",
        "profile" to "Profile section. Edit your profile. Type 'back' to return to Home.",
        "settings" to "Settings section. Adjust preferences. Type 'back' to return to Home.",
        "help" to "Help section. Find support. Type 'back' to return to Home."
    )

    fun getMessage(): String {
        return sections[currentSection] ?: "Section not found."
    }

    fun handleCommand(command: String): String {
        return when (currentSection) {
            "home" -> {
                when (command.lowercase()) {
                    "profile", "settings", "help" -> {
                        currentSection = command.lowercase()
                        getMessage()
                    }
                    "exit" -> "Thank you! Goodbye!"
                    else -> "Invalid option. Choose Profile, Settings, Help, or Exit."
                }
            }
            else -> {
                if (command.lowercase() == "back") {
                    currentSection = "home"
                    getMessage()
                } else {
                    "Invalid command. Type 'back' to return to Home."
                }
            }
        }
    }
}