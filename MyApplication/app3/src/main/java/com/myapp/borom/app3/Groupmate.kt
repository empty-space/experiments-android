package com.myapp.borom.app3

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

/*data class Groupmate(var id: Int, var name: String, var creatinDate:Date ){
}*/

/*@Entity
internal class Groupmate {
    @PrimaryKey
    private var id: Int = 0
    private var name: String? = null
    private var changeDate: String? = null
}*/
const val GROUPMATE_TABLE = "groupmate_table"


@Entity(tableName = GROUPMATE_TABLE)
class Groupmate(
            @PrimaryKey(autoGenerate = true)
            val id: Int = 0,
            var name: String,
            var date:String  = Date().toString()
            )

//object TimestampConverter {
//    private val TIME_STAMP_FORMAT: String?
//    private val df: DateFormat = SimpleDateFormat(TIME_STAMP_FORMAT);
//
//    @TypeConverter
//    @JvmStatic
//    fun fromTimestamp( value:String?):Date? {
//        if (value != null) {
//            try {
//                return df.parse(value);
//            } catch (e: ParseException) {
//            }
//            return null;
//        } else {
//            return null;
//        }
//    }
/*
object TiviTypeConverters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}*/
