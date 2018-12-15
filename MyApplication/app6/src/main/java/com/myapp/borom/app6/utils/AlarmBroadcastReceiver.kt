package com.myapp.borom.app6.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.os.VibrationEffect


class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Don't panic but your time is up!!!!.",
                Toast.LENGTH_LONG).show()
        // Vibrate the mobile phone
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(500,10))
        //////

        NotificationUtil.buildNotification(context.applicationContext, "You are being notified", "Don't panic but your time is up!!!!.")

    }
}