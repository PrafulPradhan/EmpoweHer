package com.example.empoweher.screen.events

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import com.example.empoweher.R
import com.example.empoweher.activities.Twitter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.empoweher.model.Event
import com.example.empoweher.screen.Details.converterHeight
import com.google.firebase.storage.FirebaseStorage

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventForm(){
    val context= LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var eventImage by remember {
        mutableStateOf("")
    }

    val calendar1 = Calendar.getInstance()
    calendar1.set(2023, 11, 22)
    val calendar2 = Calendar.getInstance()
    calendar2.set(2025, 11, 22)
    var startDate by remember {
        mutableLongStateOf(calendar1.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
    }
    var endDate by remember {
        mutableLongStateOf(calendar2.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
    }
    val scrollForDescription= rememberScrollState(0)
    val scrollForAddress= rememberScrollState(0)

    var hour by remember {
        mutableStateOf("")
    }
    var second by remember {
        mutableStateOf("")
    }
    var minute by remember {
        mutableStateOf("")
    }

    var duration by remember {
        mutableStateOf("")
    }

    var tag by remember { mutableStateOf("") }

    var selectedImage by remember { mutableStateOf<Uri?>(null) }

    var cost by remember{
        mutableStateOf("")
    }

    var capacity by remember{
        mutableStateOf("")
    }

    var contactNumber by remember{
        mutableStateOf("")
    }
    var btnTextStart by remember {
        mutableStateOf("Select Date")
    }
    var btnTextEnd by remember {
        mutableStateOf("Select Date")
    }

    Box (modifier = Modifier
        .fillMaxSize()

    )
    {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Text(
                    text = "Create Event",
                    fontSize = converterHeight(35, context).sp,
                    fontFamily = FontFamily(Font(R.font.font1)),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.purple_200)

                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)

            ) {
                Text(
                    text = "Event Name: ",
                    fontSize = converterHeight(23, context).sp,
                    fontFamily = FontFamily(Font(R.font.font1)),

                    )

                OutlinedTextField(
                    value = name,
                    textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                    onValueChange = { str ->
                        if(str.length<=20){
                            name = str
                        }
                        else{
                            Toast.makeText(context,"Only 20 characters Allowed",Toast.LENGTH_SHORT).show()

                        }

                    })
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)

            ) {
                Text(
                    text = "Event Description:",
                    fontSize = converterHeight(23, context).sp,
                    fontFamily = FontFamily(Font(R.font.font1)),

                    )

                OutlinedTextField(
                    value = description,
                    textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .height(converterHeight(350, context).dp)
                        .verticalScroll(scrollForDescription),
                    onValueChange = { str ->
                        if(str.length<=5000){
                            description = str
                        }
                        else{
                            Toast.makeText(context,"Only 5000 characters Allowed",Toast.LENGTH_SHORT).show()

                        }

                    })



            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)

            ) {
                Text(
                    text = "Event Address:",
                    fontSize = converterHeight(23, context).sp,
                    fontFamily = FontFamily(Font(R.font.font1)),

                    )

                OutlinedTextField(
                    value = address,
                    textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .height(converterHeight(175, context).dp)
                        .verticalScroll(scrollForAddress),
                    onValueChange = { str ->
                        if(str.length<=300){
                            address = str
                        }
                        else{
                            Toast.makeText(context,"Only 300 characters Allowed",Toast.LENGTH_SHORT).show()

                        }

                    })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)

            ) {
                Row (
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "Event City: ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),

                        )

                    OutlinedTextField(
                        value = city,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        placeholder = { Text("Example : Thane") },
                        modifier = Modifier
                            .padding(),
                        onValueChange = { str ->
                            if (str.length <= 30) {
                                city = str
                            } else {
                                Toast.makeText(
                                    context,
                                    "Only 30 characters Allowed",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        })
                }



            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ){
                // set the initial date
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar1.timeInMillis)
                var showDatePicker by remember {
                    mutableStateOf(false)
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                if(datePickerState.selectedDateMillis!!<=endDate){
                                    showDatePicker = false
                                    startDate = datePickerState.selectedDateMillis!!
                                }
                                else{
                                    Toast.makeText(context,"Start Date Should Be Earlier Than End Date",Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text(text = "Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDatePicker = false
                            }) {
                                Text(text = "Cancel")
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState
                        )
                    }
                }

                Row (
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = "Start Date: ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),

                        )
                    Spacer(modifier = Modifier.width(converterHeight(65, context).dp))
                    Button(
                        onClick = {
                            showDatePicker = true
                        }
                    ) {
                        Text(text = btnTextStart)
                    }

                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                    btnTextStart = formatter.format(Date(startDate)).toString()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ){
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar2.timeInMillis)
                var showDatePicker by remember {
                    mutableStateOf(false)
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                if (datePickerState.selectedDateMillis!!>=startDate){
                                    showDatePicker = false
                                    endDate = datePickerState.selectedDateMillis!!
                                }
                                else{
                                    Toast.makeText(context,"End Date Should End After Start Date",Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text(text = "Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDatePicker = false
                            }) {
                                Text(text = "Cancel")
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState
                        )
                    }
                }
                Row (
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = "End Date:   ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),

                        )
                    Spacer(modifier = Modifier.width(converterHeight(65, context).dp))
                    Button(
                        onClick = {
                            showDatePicker = true
                        }
                    ) {
                        Text(text = btnTextEnd)
                    }
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                    btnTextEnd = formatter.format(Date(endDate)).toString()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Event Timing: ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),

                        )
                    OutlinedTextField(
                        value = hour,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp)
                            .width(converterHeight(80, context).dp),
                        placeholder = { Text("HH", fontSize = converterHeight(20, context).sp) },
                        onValueChange = { str ->
                            if(str.isNotEmpty()){
                                if(Integer.parseInt(str) in 0..23){
                                    hour = str
                                }
                                else{
                                    Toast.makeText(context,"Enter Valid Hours",Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                hour=""
                            }


                        })
                    OutlinedTextField(
                        value = minute,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp)
                            .width(converterHeight(90, context).dp),
                        placeholder = { Text("MM", fontSize = converterHeight(18, context).sp) },
                        onValueChange = { str ->
                            if (str.isNotEmpty()) {
                                if (Integer.parseInt(str) in 0..59) {
                                    minute = str
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Enter Valid Minutes",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            } else {
                                minute = ""
                            }
                        }
                    )
                    OutlinedTextField(
                        value = second,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp)
                            .width(converterHeight(80, context).dp),
                        placeholder = { Text("SS",fontSize = converterHeight(20, context).sp) },
                        onValueChange = { str ->
                            if (str.isNotEmpty()) {
                                if (Integer.parseInt(str) in 0..59) {
                                    second = str
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Enter Valid Seconds",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            } else {
                                second = ""
                            }
                        }
                    )

                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Duration : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),

                        )
                    OutlinedTextField(
                        value = duration,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp),
                        placeholder = { Text("Enter Event Duration In Hours  ") },
                        onValueChange = { str ->
                            if(str.isNotEmpty()){
                                if(Integer.parseInt(str) in 0..12){
                                    duration = str
                                }
                                else{
                                    Toast.makeText(context,"Enter Valid Duration 1-12",Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                duration=""
                            }


                        })
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Event Tag : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                    )
                    var mExpanded by remember { mutableStateOf(false) }

                    // Create a list of cities
                    val tags = listOf("Educational", "Defence", "Exploratory", "Discussion","Empowerment", "Others")


                    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

                    // Up Icon when expanded and down icon when collapsed
                    val icon = if (mExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Column(Modifier.padding(converterHeight(20, context).dp)) {
                        // Create an Outlined Text Field with icon and not expanded
                        OutlinedTextField(
                            value = tag,
                            onValueChange = { tag = it },
                            enabled= false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    // This value is used to assign to
                                    // the DropDown the same width
                                    mTextFieldSize = coordinates.size.toSize()
                                },
                            label = {Text("Click On Arrow")},
                            trailingIcon = {
                                Icon(icon,"contentDescription",
                                    Modifier.clickable { mExpanded = !mExpanded })
                            }
                        )

                        // Create a drop-down menu with list of cities,
                        // when clicked, set the Text Field text as the city selected
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                        ) {
                            tags.forEach { label ->
                                DropdownMenuItem(
                                    text = {Text(label)},
                                    onClick = {
                                        tag = label
                                        mExpanded = false
                                    },
                                )
                            }
                        }
                    }

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                        selectedImage = uri
                    }
                    val painter = rememberAsyncImagePainter(selectedImage)

                    Text(
                        text = "Event Image : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                    )

                    Row(
                    modifier=Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(onClick = {
                            launcher.launch("image/*")
                        }) {
                            Text(text = "Click To Add Image")
                        }

                        Spacer(modifier= Modifier.width(converterHeight(100, context).dp))

                        Image(
                            painter = painter,
                            contentDescription = "Hello",
                            modifier = Modifier
                                .height(converterHeight(120, context).dp)
                                .width(converterHeight(120, context).dp)
                                .clip(RoundedCornerShape(converterHeight(10, context).dp)),
                            contentScale = ContentScale.Crop,


                        )

                    }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Event Cost in Rs : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                    )
                    OutlinedTextField(
                        value = cost,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp),
                        placeholder = { Text("Cost: ") },
                        onValueChange = { str ->
                            if(str.isNotEmpty()){
                                if(str.toInt() in 1..100000){
                                    cost = str
                                }
                                else{
                                    Toast.makeText(context,"Enter Valid Cost 1-100000",Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                cost=""
                            }
                        })

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Event Capacity : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                    )
                    OutlinedTextField(
                        value = capacity,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp,converterHeight(0, context).dp),
                        placeholder = { Text("Number Of People ") },
                        onValueChange = { str ->
                            if(str.isNotEmpty()){
                                if(str.toDouble() in 1.0..1000.0){
                                    capacity = str.toInt().toString()
                                }
                                else{
                                    Toast.makeText(context,"Enter Valid Capacity 1-1000",Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                capacity=""
                            }
                        })

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contact Number : ",
                        fontSize = converterHeight(23, context).sp,
                        fontFamily = FontFamily(Font(R.font.font1)),
                    )
                    OutlinedTextField(
                        value = contactNumber,
                        textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = converterHeight(20, context).sp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(converterHeight(2, context).dp, converterHeight(0, context).dp),
                        placeholder = { Text("Contact Number ") },
                        onValueChange = { str ->
                            if(str.isNotEmpty()){
                                if(str.length <=10){
                                    contactNumber = str
                                }
                                else{
                                    Toast.makeText(context,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show()

                                }
                            }
                            else{
                                contactNumber=""
                            }
                        })

                }

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(converterHeight(5, context).dp, converterHeight(10, context).dp)) {
                Button(onClick = {
                    if (name.isNotEmpty()  && description.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && hour.isNotEmpty() && minute.isNotEmpty() && second.isNotEmpty() && duration.isNotEmpty() && tag.isNotEmpty() && selectedImage.toString().isNotEmpty() && cost.isNotEmpty() && capacity.isNotEmpty() && contactNumber.isNotEmpty()) {
                        val dbref = FirebaseDatabase.getInstance()
                            .getReference("Event")
                        val id = dbref.push().key!!
                        val current = LocalDateTime.now()
                        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                        var vacancy = capacity
                        val e = Event(
                            id,
                            name,
                            description,
                            address,
                            city,
                            btnTextStart,
                            btnTextEnd,
                            hour + ":" + minute + ":" + second,
                            duration,
                            tag,
                            currentFirebaseUser!!.uid + "/" + current.toString(),
                            cost,
                            capacity,
                            vacancy,
                            contactNumber,
                            "" + currentFirebaseUser!!.uid
                        )
                        dbref.child(id).setValue(e);
                        val storage = FirebaseStorage.getInstance()
                        val ref = storage.getReference()
                            .child(currentFirebaseUser!!.uid + "/" + current.toString())
                        ref.putFile(selectedImage!!).addOnSuccessListener {
                            ref.getDownloadUrl().addOnSuccessListener {
                                it
                                dbref.child(id).child("eventImage").setValue(it.toString())
                                val intent = Intent(context, Twitter::class.java)
                                intent.putExtra("eventImage", it.toString())
                                intent.putExtra("eventId", id)
                                intent.putExtra("eventName",name)
                                context.startActivity(intent)
                            }
                        }
                        Toast.makeText(context, "Form Submitted", Toast.LENGTH_SHORT).show()
                        name = ""
                        description = ""
                        address = ""
                        city = ""
                        hour = ""
                        minute = ""
                        second = ""
                        duration = ""
                        tag = ""
                        selectedImage = null
                        cost = ""
                        capacity = ""
                        contactNumber = ""
                        startDate = calendar1.timeInMillis
                        endDate = calendar2.timeInMillis
                    }
                    else{
                        Toast.makeText(context,"Please Fill All Fields",Toast.LENGTH_SHORT).show()
                    }
                },modifier=Modifier.fillMaxWidth()) {
                    Text("Submit Form")
                }
            }
        }
    }
}
