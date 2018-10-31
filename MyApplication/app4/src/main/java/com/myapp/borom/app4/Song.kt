package com.myapp.borom.app4

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val SONG_TABLE = "song_table"


val dateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");

@Entity(tableName = SONG_TABLE)
class Song(
            @PrimaryKey(autoGenerate = true)
            val id: Int = 0,
            val song_name: String,
            val singer_name: String,
            val date:String  = dateFormat.format(Date())
            ){
}
