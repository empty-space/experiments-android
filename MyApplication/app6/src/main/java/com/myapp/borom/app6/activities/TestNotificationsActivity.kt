package com.myapp.borom.app6

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.myapp.borom.app6.utils.AlarmUtil
import com.myapp.borom.app6.utils.NotificationUtil
import kotlinx.android.synthetic.main.activity_test_notifications.*


class TestNotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        NotificationUtil.createNotificationChannel(this.applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_notifications)
    }

    fun onClickHitMeButton(v: View){
        var text = notification_text.text.toString()
        NotificationUtil.buildNotification(this.applicationContext, "NoTiFiCaTiOn", text)
    }

}
