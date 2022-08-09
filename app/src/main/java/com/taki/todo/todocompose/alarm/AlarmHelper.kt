package com.taki.todo.todocompose.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Immutable
import com.taki.todo.todocompose.MainActivity

object AlarmHelper {


    fun createAlarm(context: Context,timeInMillis : Long){
        val intent = Intent(context,AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent)
        }
    }

}