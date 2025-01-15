package com.example.empoweher.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.empoweher.R
import com.example.empoweher.screen.voicebot.Response
import com.example.empoweher.screen.voicebot.VoiceToTextParser
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.CoroutineScope
import java.util.Locale



class FakeCallActivity : AppCompatActivity() {

    val voiceToTextParser by lazy {
        VoiceToTextParser(application)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_fake_call)
        val mediaPlayer = MediaPlayer.create(this,R.raw.music)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        if (getActivity(this)?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.RECORD_AUDIO)
            } != PackageManager.PERMISSION_GRANTED) {
            getActivity(this)?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.RECORD_AUDIO),0
                )
            };
        }

        setContent {
            val context = LocalContext.current
            var resp by remember {mutableStateOf(false)}
                var canRecord by remember {
                    mutableStateOf(false)
                }

                val recordAudioLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        canRecord = isGranted
                    }
                )

                LaunchedEffect(key1 = recordAudioLauncher) {
                    recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            val state by voiceToTextParser.state.collectAsState()

            LaunchedEffect(key1 = resp) {
                if(resp){
                    var output = Response(state.spokenText, context)
                    Log.d("Dhruv", output)
                }
            }

            Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (state.isSpeaking) {
                            voiceToTextParser.stopListening()
                        } else {
                            voiceToTextParser.startListening()
                        }
                    }
                ){
                    AnimatedContent(targetState = state.isSpeaking) { isSpeaking->
                        if(isSpeaking){
                            Icon(imageVector = Icons.Outlined.Pause, contentDescription = "pause")
                            resp = false
//                            var output = Response(state.spokenText)
//                            Image(painter = painterResource(R.drawable.police), contentDescription = null)
                        }else{
//                            Icon(painter= painterResource(R.drawable.alert),contentDescription="play")
                            Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = "play")
                            if(state.spokenText != "") resp = true
                        }
                    }
                }
            }
        ){ padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.spokenText,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = state.error ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
//            TextToSpeechScreen()
        }
//        setContent {
//            Log.d("Raja Mausa", "before")
//            val py= Python.getInstance()
//            val module=py.getModule("app")
//            Log.d("Raja Mausa","after")
////            val eventImage=intent.getStringExtra("eventImage")
//
//            val obj = module.callAttr("driver")
//        }
    }
}
@Composable
fun TextToSpeechScreen(){
    val context= LocalContext.current
    val tts= remember { mutableStateOf<TextToSpeech?>(null) }
    var textToSpeak by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = textToSpeak,
                onValueChange = { textToSpeak = it },
                label = { Text(text = "Enter text to speak") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                if (tts.value == null) {
                    tts.value = TextToSpeech(context) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            val result = tts.value?.setLanguage(Locale("en", "IN"))
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TTS", "The specified language is not supported!")
                            } else {
                                Log.i("TTS", "TTS is initialized with Indian language!")
                            }
                        }
                    }
                }
                tts.value?.speak(textToSpeak,TextToSpeech.QUEUE_FLUSH,null)
            },modifier=Modifier.fillMaxWidth().padding(top=20.dp)) {
                Text("Speak")
            }
        }
        DisposableEffect(key1=Unit) {
            onDispose {
                tts.value?.stop()
                tts.value?.shutdown()
            }
        }
    }
}
