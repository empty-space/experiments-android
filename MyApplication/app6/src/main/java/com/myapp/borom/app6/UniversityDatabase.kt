package com.myapp.borom.app6

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull


@Database(entities = [Reminder::class], version = 2,exportSchema = false)
abstract class UniversityDatabase: RoomDatabase() {
    abstract fun ReminderDao(): ReminderDao



    companion object {


        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                PopulateDbAsync(INSTANCE!!).execute()
//            }
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                PopulateDbAsync(INSTANCE!!).execute()
//            }
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
                                .addCallback(sRoomDatabaseCallback)
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }


}
//
//private class PopulateDbAsync internal constructor(db: UniversityDatabase) : AsyncTask<Void, Void, Void>() {
//
//    private val mDao: ReminderDao = db.ReminderDao()
//
//    override fun doInBackground(vararg params: Void): Void? {
//        mDao.deleteAll()
//        var Reminder = Reminder(name="Vasil")
//        mDao.insert(Reminder)
//        Reminder = Reminder(name="Andriy")
//        mDao.insert(Reminder)
//        Reminder = Reminder(name="Ivan")
//        mDao.insert(Reminder)
//        Reminder = Reminder(name="Petro")
//        mDao.insert(Reminder)
//        Reminder = Reminder(name="Nestor")
//        mDao.insert(Reminder)
//        return null
//    }
//}