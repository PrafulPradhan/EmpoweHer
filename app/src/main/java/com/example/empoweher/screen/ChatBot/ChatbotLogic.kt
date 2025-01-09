package com.example.empoweher.screen.ChatBot

import com.example.empoweher.model.Screen

class ChatbotLogic {
    private var currentSection = "welcome"

    private val sections = mapOf(
        "home" to "The home page serves as a hub of relevant and inspiring content for users. It highlights the latest government schemes designed to empower women, providing easy access to critical information. Additionally, it features stories of women who have achieved excellence in their respective fields, serving as a source of motivation. The page also offers personalized event recommendations based on user preferences and showcases trending topics from ongoing discussions to keep users engaged and informed.",
        "welcome" to "Welcome! How can I help you?",
        "events" to "The Events Page facilitates a seamless experience for both attendees and hosts. Entrepreneurs can host events in various domains such as education, defense, sports, empowerment, and more. Event creation is straightforward: hosts fill out a simple form, and the event is then promoted on the app and shared on Twitter to attract a larger audience. For users, booking an event is quick and easy, requiring only the payment of the event fee. This page bridges the gap between knowledge seekers and providers, fostering a vibrant event ecosystem.",
        "safety" to "The app prioritizes user safety with three key functionalities:\n" +
                "\n" +
                "Fake Call: This feature triggers a fake incoming call on the user’s phone, providing an excuse to exit uncomfortable situations.\n\n" +
                "Live Location Tracking: Users can share their real-time location with emergency contacts, which can be added directly from their phone’s contact list. This ensures that help can reach them promptly if needed.\n\n" +

                "Nearest Police Station Access: The app integrates with Google Maps to display the nearest police stations based on the user’s current location, making it easy to seek assistance during emergencies.",
        "openforum" to "This feature allows users to engage in open discussions with others on the app. By joining these forums, users can seek answers to questions, especially those related to events or challenges they face. The Open Forum also acts as a networking platform, enabling users to connect with like-minded individuals, share ideas, and collaborate effectively. It’s a space for community-driven problem-solving and interaction.",

    )

    fun getMessage(): String {
        return sections[currentSection] ?: "Section not found."
    }

    fun handleCommand(command: String, navigateToNextScreen: (route: String) -> Unit) :String{
        return when (command.lowercase()) {
            "home" -> {
                sections[command]!!
            }
            "safety" ->{
                sections[command]!!
            }
            "events" ->{
                sections[command]!!
            }
            "openforum" ->{
                sections[command]!!
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