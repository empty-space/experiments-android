package com.myapp.borom.app6

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.myapp.borom.app6.utils.Converters
import java.io.Serializable
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val Reminder_TABLE = "Reminder_table"
const val Reminder_field_dateTime = "date"


@Entity(tableName = Reminder_TABLE)
class Reminder(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        var name: String,
        var date: Date? = null)
    :Serializable