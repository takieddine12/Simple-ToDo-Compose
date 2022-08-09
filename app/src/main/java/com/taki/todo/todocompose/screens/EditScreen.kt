package com.taki.todo.todocompose.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.taki.todo.todocompose.alarm.AlarmHelper
import com.taki.todo.todocompose.models.AlarmModel
import com.taki.todo.todocompose.mvvm.AlarmViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditScreen(navHostController: NavHostController, alarmViewModel: AlarmViewModel, alarmId : Long?, position : Int ){

    var initHour: Int = 0
    var initMinute: Int =  0
    var Inyear : Int = 0
    var Inmonth : Int = 0
    var Inday : Int = 0
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val state = alarmViewModel.state.collectAsState()

    // --- CHANGES ---
    val mTitle = remember { mutableStateOf("") }
    val mDate = remember { mutableStateOf("") }
    val mChecked = remember { mutableStateOf(false) }
    mChecked.value = state.value[position].isAlarmEnabled!!

    /// --- INITIAL DATE ---
    val alarmTime =state.value[position].alarmFormattedTime
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val parsedDate = format.parse(alarmTime)
    val formattedDate = format.format(parsedDate)

    val isProgress = remember { mutableStateOf(false) }
    val isIntentReady  = remember { mutableStateOf(false) }

    val deviceTime = Calendar.getInstance().timeInMillis

    Scaffold(backgroundColor = Color(0xFF1D1D23)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Edit Alarm", color = Color.White, fontSize = 19.sp,modifier = Modifier.padding(10.dp))
            TextField(value = mTitle.value,
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                placeholder = { Text(state.value[position].alarmTitle!!,color = Color.White) },
                onValueChange = {
                                mTitle.value = it
                 },modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp))
            TextField(value = mDate.value,
                placeholder = {Text(formattedDate, color = Color.White)},
                onValueChange = {
                   mDate.value = it
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.DateRange, "", tint = Color(0xFFFF8c00),
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
                                mDate.value = format.format(calendar.time)
                            }
                        })
                },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp))
            Row(modifier = Modifier.padding(top = 30.dp)) {
                Text(text = "Set A Reminder", color = Color.White, modifier = Modifier.padding(10.dp))
                Switch(checked = mChecked.value, onCheckedChange = {
                    mChecked.value = if(it){
                        it
                    } else {
                        it
                    }
                })
            }
            Button(
                modifier = Modifier.padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF8c00), contentColor = Color.White),onClick = {
                    if(mTitle.value.isEmpty() and mDate.value.isEmpty()){
                        Toast.makeText(context,"Title and Date fields cannot be empty",Toast.LENGTH_SHORT).show()
                    } else {
                        if(deviceTime < calendar.timeInMillis){
                            Toast.makeText(context,"Please choose a time in future" , Toast.LENGTH_SHORT).show()
                        } else {
                            isProgress.value = true
                            isIntentReady.value  = true
                        }
                    }
            }) {
                Text("Update Alarm")
            }

            AnimatedVisibility(visible = isProgress.value) {
                CircularProgressIndicator(color = Color(0xFFFF8c00), modifier = Modifier.padding(top = 40.dp))
            }

            if(isIntentReady.value){
                LaunchedEffect(Unit){
                    val dateFormat  = DateFormat.format("yyyy-MM-dd HH:mm",calendar.time).toString()
                    val model = AlarmModel(mTitle.value,alarmId,dateFormat,mChecked.value)
                    alarmViewModel.updateAlarm(model)
                    AlarmHelper.createAlarm(context,calendar.timeInMillis)
                    delay(3000)
                    Toast.makeText(context,"Alarm Successfully Updated ...", Toast.LENGTH_SHORT).show()
                    isProgress.value  = false
                    isIntentReady.value = false
                    navHostController.navigate(Routing.Main.route)
                }
            }
        }
    }
}

