package com.example.empoweher.composables


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.empoweher.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine


val dbref = FirebaseDatabase.getInstance()
    .getReference("Users");

//@Composable
//fun Search(){
//    var textState by remember { mutableStateOf("") }
//    Row(modifier = Modifier.fillMaxWidth()
//        ){
//        OutlinedTextField(
//            value = textState,
//            modifier = Modifier
//                .fillMaxWidth(),
//            onValueChange = {
//                textState = it
//            },
//            trailingIcon = {Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")}
//        )
//    }
//}

@Composable
fun Search(){

    val context = LocalContext.current
    var textState by remember { mutableStateOf("") }
    val searchedText = textState
    var nameList = remember { mutableStateListOf<String>("Hi", "Hello") }

//    dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            for (snapshot in dataSnapshot.children) {
//                val user = snapshot.getValue(
//                    User::class.java
//                )
//                if (user != null) {
//                    user.name?.let {
//                        nameList.add(it)
//                        Log.d("firebase",it)
//                    }
//                }
//            }
//        }
//        override fun onCancelled(databaseError: DatabaseError) {
//        }
//    })

    Row(modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            value = textState,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                textState = it
            },
            trailingIcon = {Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")}
        )
    }
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){
        items(items = nameList.filter{
            it.contains(textState)
        }, key = {it}){item->
            ColumnItem(item)
        }
    }
}

@Composable
fun ColumnItem(item:String){
    Column(modifier = Modifier.padding(10.dp)){
        Text(text=item, fontSize=22.sp, modifier = Modifier.padding(vertical=20.dp))
        Divider()
    }
}
