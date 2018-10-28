package com.myapp.borom.app3

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.content.Intent
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_new_group_mate.*

const val EXTRA_REPLY = "com.example.android.groupmatelistsql.REPLY"

class NewGroupmate : AppCompatActivity() {


    private var mEditGroupmateView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group_mate)

        mEditGroupmateView = findViewById(R.id.edit_groupmate)

        button_save.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(mEditGroupmateView!!.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val groupmate = mEditGroupmateView!!.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, groupmate)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        })
    }

}
