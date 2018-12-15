package com.myapp.borom.app6.utils

import android.arch.persistence.room.TypeConverter
import java.util.Date
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


class Converters {
    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toDate(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
//
//object TypeConverters {
//    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//
//    @TypeConverter
//    @JvmStatic
//    fun toOffsetDateTime(value: String?): OffsetDateTime? {
//        return value?.let {
//            return formatter.parse(value, OffsetDateTime::from)
//        }
//    }
//
//    @TypeConverter
//    @JvmStatic
//    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
//        return date?.format(formatter)
//    }
//}

//class Converters {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return if (value == null) null else Date(value)
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return (if (date == null) null else date!!.getTime().toLong())
//    }
//}