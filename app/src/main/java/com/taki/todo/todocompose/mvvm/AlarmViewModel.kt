package com.taki.todo.todocompose.mvvm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taki.todo.todocompose.models.AlarmModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val alarmDataSourceImpl: AlarmDataSource
) : ViewModel() {

    private var alarmState  : MutableStateFlow<MutableList<AlarmModel>> = MutableStateFlow(mutableListOf())
    val state : StateFlow<MutableList<AlarmModel>> get()  = alarmState
    fun getAlarms(){
        viewModelScope.launch {
            alarmDataSourceImpl.getAlarms().collectLatest {
                alarmState.value = it
            }
        }
    }


    fun insertAlarm(alarmModel: AlarmModel){
        viewModelScope.launch {
            alarmDataSourceImpl.insertAlarm(alarmModel)
        }
    }

    fun updateAlarm(alarmModel: AlarmModel){
        viewModelScope.launch {
            alarmDataSourceImpl.updateAlarm(alarmModel)
        }
    }

    fun deleteAlarms(){
        viewModelScope.launch {
            alarmDataSourceImpl.deleteAlarms()
        }
    }

    fun deleteAlarm(mAlarmTime : Long){
        viewModelScope.launch {
            alarmDataSourceImpl.deleteAlarm(mAlarmTime)
        }
    }
}