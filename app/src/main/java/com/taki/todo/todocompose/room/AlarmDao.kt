package com.taki.todo.todocompose.room

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.*
import com.taki.todo.todocompose.models.AlarmModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm ORDER BY alarmTime")
    fun getAlarms() : Flow<MutableList<AlarmModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmModel: AlarmModel)

    @Update
    suspend fun updateAlarm(alarmModel: AlarmModel)

    @Query("DELETE FROM alarm")
    suspend fun deleteAlarms()

    @Query("DELETE FROM alarm WHERE alarmTime = :mAlarmTime")
    suspend fun deleteAlarm(mAlarmTime : Long)
}