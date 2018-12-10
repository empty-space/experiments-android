package com.myapp.borom.app6.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatters{
    @JvmStatic
    fun Date(d:Date):String{
        val formatter = SimpleDateFormat("YYYY/MM/dd HH:mm")
        return  formatter.format(d)
    }
    fun DateOrEmpty(d:Date?):String{
        val formatter = SimpleDateFormat("YYYY/MM/dd HH:mm")
        return  if (d!=null) formatter.format(d) else ""
    }
}