package com.taki.todo.todocompose.koin

import android.content.Context
import androidx.room.Room
import com.taki.todo.todocompose.mvvm.AlarmDataSource
import com.taki.todo.todocompose.mvvm.AlarmDataSourceImpl
import com.taki.todo.todocompose.mvvm.AlarmViewModel
import com.taki.todo.todocompose.room.AlarmDao
import com.taki.todo.todocompose.room.AlarmDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koin_module  = module {
    single { getRoomInstance(get()) }
    single { getDaoInstance(get()) }
    single<AlarmDataSource> { AlarmDataSourceImpl(get()) }
    viewModel { AlarmViewModel(get()) }
}

private fun getRoomInstance(context: Context) = Room.databaseBuilder(context,AlarmDatabase::class.java,"alarm.db")
    .fallbackToDestructiveMigration()
    .build()

private fun getDaoInstance(alarmDatabase: AlarmDatabase) = alarmDatabase.alarmDao()

