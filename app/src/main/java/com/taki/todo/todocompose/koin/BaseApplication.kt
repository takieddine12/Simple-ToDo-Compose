package com.taki.todo.todocompose.koin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.taki.todo.todocompose.Utils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotification()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@BaseApplication)
            modules(listOf(koin_module))
        }
    }

    private fun createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelNotification = NotificationChannel(
                Utils.NOTIFICATION_ID, Utils.NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)

            channelNotification.enableVibration(true)
            channelNotification.vibrationPattern = longArrayOf(1000,500,1000)
            channelNotification.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelNotification)
        }
    }
}