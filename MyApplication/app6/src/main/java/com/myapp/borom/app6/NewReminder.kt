package com.myapp.borom.app6

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.content.Intent
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_new_reminder.*

const val EXTRA_REPLY = "com.example.android.Reminderlistsql.REPLY"

class NewReminder : AppCompatActivity() {


    private var mEditReminderView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)

        mEditReminderView = findViewById(R.id.edit_Reminder)

        button_save.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(mEditReminderView!!.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val Reminder = mEditReminderView!!.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, Reminder)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        })
    }

}
