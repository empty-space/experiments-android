package com.myapp.borom.app7

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToActivity1(view: View) {
        startActivity(Intent(this, activity1::class.java))
    }
    fun goToActivity2(view: View) {
        startActivity(Intent(this, activity2::class.java))
    }
    fun goToActivity3(view: View) {
        startActivity(Intent(this, activity3::class.java))
    }
    fun exit(view: View) {
        finish()
    }
}
