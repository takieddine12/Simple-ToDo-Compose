package com.taki.todo.todocompose.mvvm

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.taki.todo.todocompose.models.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmDataSource {

    suspend fun getAlarms() : Flow<MutableList<AlarmModel>>
    suspend fun insertAlarm(alarmModel: AlarmModel)
    suspend fun updateAlarm(alarmModel: AlarmModel)
    suspend fun deleteAlarms()
    suspend fun deleteAlarm(mAlarmTime : Long)
}