package com.myapp.borom.app6.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import java.util.*
import com.myapp.borom.app6.MainActivity
import android.widget.Toast
import android.content.Context.ALARM_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.EditText



object AlarmUtil{
    //could be any here
    const val REQUEST_CODE = 0;

    fun setAlarm(context: Context, dateTime: Date,name:String ){
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 234324243, intent, 0)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager!!.set(AlarmManager.RTC_WAKEUP, dateTime.time, pendingIntent)
//        Toast.makeText(this, "Alarm set in $i seconds",
//                Toast.LENGTH_LONG).show()

//        val intent = Intent(context, MainActivity::class.java)
//        intent.action = Intent.ACTION_MAIN
//        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//
//        // BEGIN_INCLUDE (pending_intent_for_alarm)
//        // Because the intent must be fired by a system service from outside the application,
//        // it's necessary to wrap it in a PendingIntent.  Providing a different process with
//        // a PendingIntent gives that other process permission to fire the intent that this
//        // application has created.
//        // Also, this code creates a PendingIntent to start an Activity.  To create a
//        // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
//        val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, 0)
//
//
//        ///
//        var alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
////        var alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
////            PendingIntent.getBroadcast(context, 0, intent, 0)
////        }
//
//        alarmMgr?.setExact(
//                AlarmManager.RTC,
//                dateTime.time,
//                pendingIntent
//        )
    }
}

class AlarmReceiver{

}