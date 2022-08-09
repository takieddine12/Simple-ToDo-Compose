package com.taki.todo.todocompose.mvvm

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.taki.todo.todocompose.models.AlarmModel
import com.taki.todo.todocompose.room.AlarmDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AlarmDataSourceImpl(
    private val alarmDao: AlarmDao
) : AlarmDataSource{
    override suspend fun getAlarms(): Flow<MutableList<AlarmModel>> {
        return flow { emit(alarmDao.getAlarms().first()) }
    }

    override suspend fun insertAlarm(alarmModel: AlarmModel) {
        alarmDao.insertAlarm(alarmModel)
    }

    override suspend fun updateAlarm(alarmModel: AlarmModel) {
        alarmDao.updateAlarm(alarmModel)
    }

    override suspend fun deleteAlarms() {
        alarmDao.deleteAlarms()
    }

    override suspend fun deleteAlarm(mAlarmTime : Long) {
        alarmDao.deleteAlarm(mAlarmTime)
    }
}