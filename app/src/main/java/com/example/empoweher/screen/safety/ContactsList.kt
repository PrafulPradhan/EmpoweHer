package com.example.empoweher.screen.safety

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.empoweher.SQLIteDB.Contact 
import com.example.empoweher.SQLIteDB.ContactDatabase
import com.example.empoweher.composables.ContactCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.empoweher.R
import com.example.empoweher.activities.ContactActivity

@SuppressLint("CoroutineCreationDuringComposition")

    @Composable
    fun ContactsList(navigateToNextScreen: (route: String) -> Unit) {
        val context = LocalContext.current
        val database =
            Room.databaseBuilder(context, ContactDatabase::class.java, "contacts").build()
        var List by remember { mutableStateOf(emptyList<Contact>()) }
        var scope = rememberCoroutineScope()
        var key by remember {
            mutableStateOf(0)
        }

        LaunchedEffect(key % 2 == 0) {
            scope.launch(Dispatchers.IO) {
                List = database.itemDao().getAllItems().toMutableList()
            }
        }
        if (List.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream))
            ) {
                Text(
                    text = "No Contacts Saved!!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.font1)),
                )
                Spacer(modifier = Modifier.height(170.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add_contact),
                            contentDescription = "cd",
                            modifier = Modifier
                                .fillMaxWidth(.4f)
                                .clickable {
                                    val navigate = Intent(context, ContactActivity::class.java)
                                    context.startActivity(navigate)
                                }
                        )
                        Text(
                            text = "Click To Add Contact", modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.font1))
                        )
                    }
                }
            }
        } else {
//        Button(onClick = {
//            navigateToNextScreen(Screen.UpdateContactList.route)
//        }) {
//
//        }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cream))
            ) {
                Text(
                    text = "Saved Contacts", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.font1))
                )
                lazy(list = List.toMutableList(), { key++ })
            }

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun lazy(list: MutableList<Contact>, increment: () -> Unit) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val database =
            Room.databaseBuilder(context, ContactDatabase::class.java, "contacts").build()
        val openDialog = remember { mutableStateOf(false) }


        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cream)), state = rememberLazyListState(),
            content = {
                itemsIndexed(items = list, key = { _, listItem ->
                    listItem.hashCode()
                }) { index, item ->

                    val state = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                Log.d("deleteContact1", item.firstName)

                                scope.launch(Dispatchers.IO) {
                                    database.itemDao().deleteContact(item)
                                    increment()
                                    Log.d("deleteContact2", item.firstName)
                                }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> colorResource(id = R.color.redorange)
                                DismissDirection.EndToStart -> Color.DarkGray
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            ContactCard(
                                fName = item.firstName,
                                lName = item.lastName,
                                pNum = item.phoneNumber,
                                checked = item.emergency
                            )
                        },
                        directions = setOf(DismissDirection.StartToEnd)
                    )
                }
            })
    }
