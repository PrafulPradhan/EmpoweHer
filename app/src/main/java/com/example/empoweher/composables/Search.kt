package com.example.empoweher.composables


import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Verified
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.empoweher.model.DataState
import com.example.empoweher.model.Screen
import com.example.empoweher.model.User
import com.example.empoweher.screen.Details.converterHeight
import com.example.empoweher.viewmodel.ProfileViewModel
import com.example.empoweher.viewmodel.mainviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine


val dbref = FirebaseDatabase.getInstance()
    .getReference("Users");
val currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid

@Composable
fun Search(navigateToNextScreen: (route: String)->Unit){
    val viewModel= viewModel{ ProfileViewModel() }
    val context = LocalContext.current
    var textState by remember { mutableStateOf("") }
    var userList = remember { mutableStateListOf<User>() }
    Row(modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            value = textState,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                textState = it
            },
            placeholder = { Text("Search For Any Profile")},
            trailingIcon = {Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")}
        )
    }
    val filteredList = userList
        .filter { it.name!!.contains(textState, ignoreCase = true) }
        .distinct()
    when( val result= viewModel.response.value){
        is DataState.Loading -> {

        }
        is DataState.SuccessUser->{
            Spacer(Modifier.height(converterHeight(5,context).dp))
            SampleText(text="Top Entrepreneurs")
            LazyColumn(
                modifier = Modifier.padding(10.dp)
            ){
                itemsIndexed(
                    items=result.data.filter { it.name!!.contains(textState, ignoreCase = true)}.distinct(),
                    key={index,item -> "$item-$index"}
                ){index,item->
                    Log.d("ent",item.toString())
                    val isEnt = getInfoUser(thing = "isEnt", userId = item.userID!!)
                    ColumnItem(item.name!!,item.userID!!,context,navigateToNextScreen, isEnt)
                }
            }
        }
        is DataState.Failure->{

        }

        DataState.Empty -> TODO()
        is DataState.Success -> TODO()
        is DataState.SuccessAnswer -> TODO()
        is DataState.SuccessQuestion -> TODO()
        is DataState.SuccessSlot -> TODO()
        is DataState.SuccessSlots -> TODO()
    }
}

@Composable
fun ColumnItem(item:String,userid:String,context: Context,navigateToNextScreen: (route: String)->Unit, isEntrepreneur: String){
    Column(modifier = Modifier
        .padding(10.dp)
        .clickable{   navigateToNextScreen(Screen.Profile.route + "/" + userid) }
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start){
            Text(text=item, fontSize= converterHeight(22,context).sp, modifier = Modifier.padding(vertical=20.dp))
            if(isEntrepreneur=="true"){
                Icon(imageVector = Icons.Outlined.Verified, contentDescription = "verified",modifier=Modifier.offset(y= 22.dp))
            }
        }
        Divider()
    }
}
