package com.example.empoweher.screen.app

import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.empoweher.activities.ContactActivity
import com.example.empoweher.activities.SafetyActivity
import com.example.empoweher.auth.signin.GoogleAuthUiClient
import com.example.empoweher.auth.signin.SignInScreen
import com.example.empoweher.auth.signin.SignInViewModel
import com.example.empoweher.composables.DetailedEventCard
import com.example.empoweher.composables.EventCard
import com.example.empoweher.composables.getInfo
import com.example.empoweher.composables.getValue
import com.example.empoweher.composables.onBoarding
import com.example.empoweher.model.Screen
import com.example.empoweher.screen.Details.Details
import com.example.empoweher.screen.Details.DetailsDesignation
import com.example.empoweher.screen.Details.DetailsDp
import com.example.empoweher.screen.Details.DetailsInterests
import com.example.empoweher.screen.UpdateContactList
import com.example.empoweher.screen.ask.Answer
import com.example.empoweher.screen.ask.Ask
import com.example.empoweher.screen.events.EventForm
import com.example.empoweher.screen.events.Events
import com.example.empoweher.screen.home.Home
import com.example.empoweher.screen.profile.Profile
import com.example.empoweher.screen.safety.*
import com.example.empoweher.screen.temp.Temp1
import com.example.empoweher.screen.Details.Registration
import com.example.empoweher.screen.ask.AskQuestion
import com.example.empoweher.screen.ask.GiveAnswer
import com.example.empoweher.screen.events.BookedEvents
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    googleAuthUiClient: GoogleAuthUiClient,
    lifecycleScope: LifecycleCoroutineScope,
) {

    val navController = rememberNavController()

    var shouldShowScaffold by remember {
        mutableStateOf(true)
    }

    /**
     * Checks if User is already logged in
     */
    val startDestination = if (googleAuthUiClient.getSignedInUser() != null) {
        Screen.Home.route
    } else {
        Screen.Login.route
//        Screen.Home.route
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                if (shouldShowScaffold) {
                    BottomNavigation(
                        navigateToItem = { route ->
                            navController.navigate(route = route)
                        }
                    )
                }
                /*composable(route = Screen.ScreenName.route) {
                    ScreenName()
                }*/

            }
        ) { paddingValues ->
            println(paddingValues)

            NavHost(navController = navController, startDestination = startDestination) {

                composable(Screen.Login.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == ComponentActivity.RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                    SignInScreen(
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        },
                        navigateToHome = {
                            navController.navigate(Screen.Details.route)
                            viewModel.resetState()
                        },
//                        navigateToDetails={
//                            navController.navigate(Screen.Details.route)
//                            viewModel.resetState()
//                        }
                    )
                }
                composable(route = Screen.Home.route) {
                    Home(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = true
                    }
                }
                composable(route = Screen.Safety.route) {
                    Safety(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
//                    val context = LocalContext.current
//                    LaunchedEffect(key1=Unit) {
//                        val navigator = Intent(context, SafetyActivity::class.java)
//                        context.startActivity(navigator)
//                    }
                }
                composable(route = Screen.FakeCall.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    FakeCall()
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }
                composable(route = Screen.AskQuestion.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    AskQuestion()
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }

                composable(route = Screen.Map.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    map()
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }


                composable(route = Screen.Events.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    Events(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }
                composable(route = Screen.AddContact.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    AddContact()
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }
                composable(route = Screen.EventForm.route) {
                    LaunchedEffect(key1 = shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    EventForm()
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                }

                composable(route = Screen.ContactsList.route) {
                    LaunchedEffect(key1 = Unit){
                        shouldShowScaffold = false
                    }
                    ContactsList( navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })
                    DisposableEffect(Unit) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                }

                composable(route = Screen.UpdateContactList.route+"/{email}",arguments = listOf(
                    navArgument("email"){
                        type = NavType.StringType
                    }
                )) {
                    val email = it.arguments!!.getString("email")
                    if (email != null) {
                        UpdateContactList(email,
                            navigateToNextScreen = { route ->
                                navController.navigate(route)
                            })
                    }
                }

                composable(route = Screen.DetailedEventCard.route+"/{eventId}", arguments = listOf(
                    navArgument("eventId"){
                        type = NavType.StringType
                        nullable=true
                    }
                )) {
                    LaunchedEffect(key1 = shouldShowScaffold) {
                    shouldShowScaffold = false
                }
                    val eventId = it.arguments?.getString("eventId")
                    DetailedEventCard(eventId, navigateToNextScreen = { route ->
                            navController.navigate(route)
                        })

                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }

                composable(route = Screen.EventCard.route) {
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    EventCard(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                }

                composable(route = Screen.Temp1.route) {
                    Temp1()
                }

                composable(route = Screen.ContactOption.route) {
                    ContactOption(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
                }

                composable(route = Screen.Alerts.route) {
                    Alerts(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
                }

                composable(route = Screen.Ask.route) {
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    Ask(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }

                composable(route = Screen.Details.route) {
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    Details(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )

                }
                composable(route = Screen.DetailsDesignation.route) {

                    DetailsDesignation(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )

                }
                composable(route = Screen.DetailsInterests.route) {
                    DetailsInterests(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })


                }
                composable(route = Screen.DetailsDp.route) {
                    DetailsDp(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                }
                composable(route = Screen.Registration.route) {
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    Registration(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }
                }
                composable(route = Screen.Answer.route+"/{questionId}", arguments = listOf(
                    navArgument("questionId"){
                        type = NavType.StringType
                        nullable=true
                    }
                )) {
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    val questionId = it.arguments?.getString("questionId")
                    Answer(questionId, navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })

                }
                composable(route = Screen.Profile.route+"/{userId}", arguments = listOf(
                    navArgument("userId"){
                        type = NavType.StringType
                        nullable=true
                    }
                )) {
                    val userId = it.arguments?.getString("userId")
                    Profile(userId, navigateToNextScreen = { route ->
                        navController.navigate(route){
                            popUpTo(0)
                        }
                    })
                }

                composable(route = Screen.Onboarding.route) {
                    onBoarding(
                        navigateToNextScreen = { route ->
                            navController.navigate(route)
                        }
                    )
                    LaunchedEffect(shouldShowScaffold){
                        shouldShowScaffold = false
                    }
                    DisposableEffect(shouldShowScaffold) {
                        onDispose {
                            shouldShowScaffold = true
                        }
                    }

                }

                composable(route = Screen.GiveAnswer.route+"/{questionId}",arguments = listOf(
                    navArgument("questionId"){
                        type = NavType.StringType
                    }
                )) {
                    val questionId = it.arguments!!.getString("questionId")
                    if (questionId!= null) {
                        GiveAnswer(questionId = questionId,
                            navigateToNextScreen = { route ->
                                navController.navigate(route)
                            })
                    }
                }

                composable(route = Screen.BookedEvents.route) {
                    BookedEvents(navigateToNextScreen = { route ->
                        navController.navigate(route)
                    })

                }





            }
        }
    }
}