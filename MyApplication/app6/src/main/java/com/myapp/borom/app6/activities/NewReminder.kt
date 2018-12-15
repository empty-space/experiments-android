package com.myapp.borom.app6

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.content.Intent
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import com.myapp.borom.app6.fragments.TimePickerFragment
import com.myapp.borom.app6.utils.Formatters
import kotlinx.android.synthetic.main.activity_new_reminder.*
import java.util.Date

const val EXTRA_REPLY = "com.example.android.Reminderlistsql.REPLY"

class NewReminder : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private var mEditReminderView: EditText? = null
    private var mEditDateView: EditText? = null
    private var dateTime: Date = Date().apply { minutes+=1 };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)

        mEditReminderView = findViewById(R.id.edit_Reminder)
        mEditReminderView?.setText("New reminder")
        mEditDateView = findViewById(R.id.edit_Date)
        updateDateTimeOnView()
//        mEditDateView!!.setOnTouchListener { v, event ->
//            showDatePickerDialog(v!!)
//            true
//        }
//        mEditDateView!!.setOnFocusChangeListener { v, hasFocus -> {if(hasFocus){showDatePickerDialog(v!!)}} }
//        (object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                showDatePickerDialog(v!!)
//                return true
//            }});

        button_save.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(mEditReminderView!!.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val name = mEditReminderView!!.text.toString()
                    val newReminder = Reminder(name = name, date= dateTime)

                    replyIntent.putExtra(EXTRA_REPLY,newReminder)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        })
    }

    fun showDatePickerDialog(v: View) {
        var datePick = DatePickerDialog(this,this, dateTime.year+1900,dateTime.month,dateTime.day)
        datePick.show()
    }
    fun showTimePickerDialog() {
        var timePick = TimePickerDialog(this,this,dateTime.hours,dateTime.minutes,true)
        timePick.show()
//        timePick.setOnTimePicked { hourOfDay, minute -> {} };
//        timePick.show(supportFragmentManager, "timePicker")
    }
    fun updateDateTimeOnView(){
        mEditDateView?.setText(Formatters.DateOrEmpty(dateTime))
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateTime.year = year-1900;
        dateTime.month = month;
        dateTime.date = dayOfMonth
        showTimePickerDialog()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTime.hours = hourOfDay
        dateTime.minutes = minute
        updateDateTimeOnView()
    }

}
