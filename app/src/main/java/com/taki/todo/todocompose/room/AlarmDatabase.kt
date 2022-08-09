package com.taki.todo.todocompose.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taki.todo.todocompose.models.AlarmModel

@Database(entities = [AlarmModel::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao() : AlarmDao
}