package com.myapp.borom.app6

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.AsyncTask
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull
import com.myapp.borom.app6.utils.*


@Database(entities = [Reminder::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class UniversityDatabase: RoomDatabase() {
    abstract fun ReminderDao(): ReminderDao

    companion object {
        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
        }

        /**
         * This is just for singleton pattern
         */
        private var INSTANCE: UniversityDatabase? = null

        fun getDatabase(context: Context): UniversityDatabase {
            if (INSTANCE == null) {
                synchronized(UniversityDatabase::class.java) {
                    if (INSTANCE == null) {
                        // Get PhraseRoomDatabase database instance
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                UniversityDatabase::class.java, "University_database"
                                )
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .addCallback(sRoomDatabaseCallback)
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }


}