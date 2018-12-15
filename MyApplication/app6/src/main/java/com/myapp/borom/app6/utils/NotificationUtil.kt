package com.myapp.borom.app6.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.myapp.borom.app6.R

const val CHANNEL_ID = "app6_channel"

object NotificationUtil{
    var i=0;
    fun buildNotification(context:Context,textTitle:String, textContent:String){
        var mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setLights(Int.MAX_VALUE,100,200)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(i++, mBuilder.build())
        }
    }
    fun createNotificationChannel(context:Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getResources().getString(R.string.channel_name)
            val descriptionText = context.getResources().getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}