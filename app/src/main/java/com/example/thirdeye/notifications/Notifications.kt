package com.example.thirdeye.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.thirdeye.R

object Notifications {
    const val FOREGROUND_CHANNEL ="cam_channel"
    const val CHANNEL_INTRUDER ="intruder_alert"

    fun createChannels(context: Context){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val nm=context.getSystemService(NotificationManager::class.java)

            val fg= NotificationChannel(
                FOREGROUND_CHANNEL,
                "Background Notifications",
                NotificationManager.IMPORTANCE_DEFAULT

            )
            val intr= NotificationChannel(
                CHANNEL_INTRUDER,
                "Intruder Notifications",
                NotificationManager.IMPORTANCE_HIGH

            )
            nm?.createNotificationChannel(fg)

            nm?.createNotificationChannel(intr)
        }


    }
    fun persistentNotification(context: Context): Notification{
        return Notification.Builder(context,FOREGROUND_CHANNEL)
            .setContentTitle(context.getString(R.string.fgNotificationTitle))
            .setContentText(context.getString(R.string.fgNotifictaionText))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .build()


    }
    fun intrusionNotification(context: Context): Notification{
        return Notification.Builder(context,CHANNEL_INTRUDER)
            .setContentTitle(context.getString(R.string.IntrusionNotificationTitle))
            .setContentText(context.getString(R.string.intruderNotifiationText))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

            .build()
    }


}