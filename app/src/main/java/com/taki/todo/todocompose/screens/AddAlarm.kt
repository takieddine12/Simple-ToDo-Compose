package com.taki.todo.todocompose.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.taki.todo.todocompose.alarm.AlarmHelper
import com.taki.todo.todocompose.models.AlarmModel
import com.taki.todo.todocompose.mvvm.AlarmViewModel
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddAlarm(navHostController: NavHostController,alarmViewModel: AlarmViewModel){
    var initHour: Int = 0
    var initMinute: Int =  0
    var Inyear : Int = 0
    var Inmonth : Int = 0
    var Inday : Int = 0
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val title = remember { mutableStateOf("")}
    val isChecked = remember { mutableStateOf(false)}
    val fullDate = remember { mutableStateOf("")}
    val isProgressShown = remember { mutableStateOf(false) }
    val isIntentReady = remember { mutableStateOf(false) }

    Scaffold(backgroundColor = Color(0xFF1D1D23)) {
         Column(horizontalAlignment = Alignment.CenterHorizontally) {
             Text("Add Alarm", color = Color.White, fontSize = 20.sp,  modifier = Modifier.padding(10.dp))

             TextField(value = title.value, colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                 label = { Text(text = "Task", fontStyle = FontStyle.Italic, color = Color.White)},
                 onValueChange = {
                 title.value = it
             },modifier = Modifier
                     .fillMaxWidth()
                     .padding(start = 10.dp, end = 10.dp, top = 10.dp))

             TextField(value = fullDate.value,
                 onValueChange = {},
                 label = { Text(text = "Date", fontStyle = FontStyle.Italic, color = Color.White)},
                 trailingIcon = {
                     Icon(Icons.Filled.DateRange, "", tint = Color(0xFFFF8c00),
                     modifier = Modifier.clickable {

                         // Date Picker
                         val datePicker = DatePickerDialog(context,{ _, year: Int, month: Int, day: Int ->
                                 Inyear = year
                                 Inmonth = month
                                 Inday = day
                             } , 2022 , 5 , 30 ,)

                         datePicker.show()

                         val timePickerDialog = TimePickerDialog(context, {_, hour : Int, minute: Int ->
                                 initHour = hour
                                 initMinute = minute
                             },  0,0, true)

                         datePicker.setOnDismissListener {
                             timePickerDialog.show()
                         }

                         timePickerDialog.setOnDismissListener {
                             calendar.set(Inyear,Inmonth,Inday,initHour,initMinute)
                             val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
                             fullDate.value = format.format(calendar.time)
                         }
                     })
                 },
                 colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(start = 10.dp, end = 10.dp, top = 10.dp))

             Row(modifier = Modifier.padding(top = 30.dp)) {
                Text(text = "Set A Reminder", color = Color.White, modifier = Modifier.padding(10.dp))
                Switch(checked = isChecked.value, onCheckedChange = {
                    if(it){
                        isChecked.value = it
                    } else {
                        isChecked.value = it
                    }
                })
             }

             Button(modifier = Modifier.padding(top = 20.dp),
                 colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF8c00), contentColor = Color.White),
                 onClick = {
                 isProgressShown.value = true
                 val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                 val date = formattedDate.format(calendar.time)
                 val alarmModel = AlarmModel(title.value,calendar.timeInMillis,date,isChecked.value)
                 alarmViewModel.insertAlarm(alarmModel)
                 //AlarmHelper.createAlarm(context,calendar.timeInMillis)
                 isIntentReady.value = true

             }) {
                 Text("Set Alarm")
             }

             AnimatedVisibility(visible = isProgressShown.value) {
                 CircularProgressIndicator(color = Color(0xFFFF8c00), modifier = Modifier.padding(top = 40.dp))
             }

             if(isIntentReady.value){
                 Toast.makeText(context,"Alarm Successfully Added..",Toast.LENGTH_SHORT).show()
                 LaunchedEffect(Unit){
                     delay(3000)
                     isProgressShown.value = false
                     isIntentReady.value = false
                     navHostController.navigate("Main")
                 }
             }
         }
     }
}
