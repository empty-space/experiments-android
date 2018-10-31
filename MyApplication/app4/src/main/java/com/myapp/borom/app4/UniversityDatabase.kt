package com.myapp.borom.app4

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull


@Database(entities = [Song::class], version = 2,exportSchema = false)
abstract class UniversityDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao



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
//    private val mDao: SongDao = db.songDao()
//
//    override fun doInBackground(vararg params: Void): Void? {
//        mDao.deleteAll()
//        var song = Song(name="Vasil")
//        mDao.insert(song)
//        song = Song(name="Andriy")
//        mDao.insert(song)
//        song = Song(name="Ivan")
//        mDao.insert(song)
//        song = Song(name="Petro")
//        mDao.insert(song)
//        song = Song(name="Nestor")
//        mDao.insert(song)
//        return null
//    }
//}