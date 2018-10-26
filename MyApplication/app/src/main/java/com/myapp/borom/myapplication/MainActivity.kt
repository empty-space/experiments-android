package com.myapp.borom.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.myapp.extra.MESSAGE"

class MainActivity : AppCompatActivity() {
    val list_hello_kotlin = arrayOf("Hello Kotlin World!","Hello!","Hello Andri!")
    var hello_index=0

    fun getNextHello():String{
        hello_index=(hello_index+1) % list_hello_kotlin.size
        return list_hello_kotlin[hello_index]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //make link working
        link_github.setMovementMethod(LinkMovementMethod.getInstance());
        /*btn_go.setOnClickListener {
            //editTextField.setText(getNextHello())
        }*/
    }

    fun go(view: View) {
        val message = editTextField.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}
