package com.taki.todo.todocompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmModel(
    val alarmTitle : String? = null,
    @PrimaryKey(autoGenerate = false)
    val alarmTime : Long? = null,
    val alarmFormattedTime : String? = null,
    var isAlarmEnabled : Boolean? = null
)