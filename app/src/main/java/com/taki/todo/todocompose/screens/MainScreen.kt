package com.taki.todo.todocompose.screens

import com.taki.todo.todocompose.R
import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.taki.todo.todocompose.models.AlarmModel
import com.taki.todo.todocompose.mvvm.AlarmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen (
    navController : NavHostController,
    alarmsList : State<MutableList<AlarmModel>?>,
    alarmViewModel: AlarmViewModel){

    val isShown = remember { mutableStateOf(false) }
    val alarmId = remember { mutableStateOf(0L) }

    Column {
        SetUpperBar(navController,alarmViewModel)
        if(alarmsList.value?.size  == 0){
            Box( contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    painter = painterResource(id = R.drawable.empty_img),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color(0xFFFF8c00)),
                    contentDescription = "")
            }
        }
        else {
            LazyVerticalGrid(cells = GridCells.Fixed(2),
                modifier = Modifier.padding(10.dp)){
                alarmsList.value?.size?.let {
                    items(it){ position ->
                        Card(elevation = 10.dp, backgroundColor = Color(0xFF34344A),modifier = Modifier
                            .padding(5.dp)
                            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            .combinedClickable(
                                onClick = {
                                    navController.navigate(Routing.Edit.route + "/" + alarmViewModel.state.value[position].alarmTime + "/" + position)
                                },
                                onLongClick = {
                                    alarmId.value = alarmViewModel.state.value[position].alarmTime!!
                                    isShown.value = true
                                }
                            )) {
                            Column {
                                Text(
                                    alarmsList.value!![position].alarmTitle!!,
                                    modifier = Modifier.padding(10.dp),
                                    color = Color.White
                                )
                                Text(
                                    alarmsList.value!![position].alarmFormattedTime!!,
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    if(isShown.value){
        ShowDeleteDialog(isShown = isShown, alarmViewModel = alarmViewModel,alarmId.value)
    }
}

@Composable
fun ShowDeleteDialog(isShown : MutableState<Boolean>,alarmViewModel: AlarmViewModel,alarmId : Long){
    AlertDialog(
        onDismissRequest = { isShown.value = false },
        confirmButton = {
            TextButton(onClick = {
                alarmViewModel.deleteAlarm(alarmId)
                isShown.value = false
            }) {
                Text("Remove")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                isShown.value = false
            }) {
                Text("Dismiss")
            }
        },
        title = {  Text("Remove Alarm")},
        text = { Text("Do you want really want to remove alarm ?")})
}



@Composable()
fun SetUpperBar(navController: NavController,alarmViewModel: AlarmViewModel){
    val context = LocalContext.current
    val isDialogShown = remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()) {

        Text("Task Reminder", color = Color.White, fontSize = 20.sp ,modifier = Modifier
            .padding(5.dp)
            .weight(2f))
        IconButton(onClick = { navController.navigate("Add") }) {
            Icon(Icons.Filled.Add,"", tint = Color(0xFFFFFFFF))
        }
        IconButton(onClick = {
            if (alarmViewModel.state.value.isNotEmpty()){
                isDialogShown.value = true
            } else {
                isDialogShown.value = false
                Toast.makeText(context,"No alarms",Toast.LENGTH_SHORT).show()
            }

        }) {
            Icon(Icons.Filled.Delete,"", tint = Color(0xFFFFFFFF))
        }


        if(isDialogShown.value){
            AlertDialog(
                onDismissRequest = { isDialogShown.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        alarmViewModel.deleteAlarms()
                        isDialogShown.value = false
                        alarmViewModel.getAlarms()
                        Toast.makeText(context,"Alarms Successfully Removed",Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Remove")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {  isDialogShown.value = false }) {
                        Text("Dismiss")
                    }
                },
                title = { Text(text = "Remove Alarms")},
                text = { Text(text = "Do you really want to remove alarms ?")})
        }

    }
}


/*

 */