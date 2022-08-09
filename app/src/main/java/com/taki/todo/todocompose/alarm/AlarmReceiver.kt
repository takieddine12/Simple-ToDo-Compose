package com.taki.todo.todocompose.alarm

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.taki.todo.todocompose.MainActivity
import com.taki.todo.todocompose.R
import com.taki.todo.todocompose.Utils

class AlarmReceiver : BroadcastReceiver() {
    private var wakeLock : PowerManager.WakeLock? = null
    private lateinit var notificationManager: NotificationManager
    override fun onReceive(context: Context?, intent: Intent?) {
        turnOnScreen(context!!)
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        fireNotification(context, "Time For Task","Wake Up")
        fireNotification(context,"","")
    }

    private fun fireNotification(context: Context?,time : String , prayer : String) {
        val intent  = Intent(context, MainActivity::class.java)
        val pendingIntent = getActivity(context,0,intent,0)
        val notificationCompat = NotificationCompat.Builder(context!!, Utils.NOTIFICATION_ID)
            .setContentTitle(prayer)
            .setContentText(time)
            .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(1,notificationCompat)

    }

    @SuppressLint("InvalidWakeLockTag")
    private fun turnOnScreen(context: Context){
        if(wakeLock != null) wakeLock!!.release()
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE, ""
        )
        wakeLock!!.acquire()
    }
}