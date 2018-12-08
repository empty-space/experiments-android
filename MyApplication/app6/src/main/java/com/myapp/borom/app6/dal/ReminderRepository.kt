package com.myapp.borom.app6

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.os.AsyncTask.execute


class ReminderRepository(val application: Application){
    val mReminderDao: ReminderDao
    val mAllReminders: LiveData<List<Reminder>>

    init{
        val db = UniversityDatabase.getDatabase(application);
        mReminderDao = db.ReminderDao();
        mAllReminders = mReminderDao.getAll();
    }

    fun insert(Reminder: Reminder) {
        insertAsyncTask(mReminderDao).execute(Reminder)
    }
    fun update(Reminder: Reminder) {
        mReminderDao.update(Reminder)
    }
    fun delete(Reminder: Reminder) {
        deleteAsyncTask(mReminderDao).execute(Reminder)
    }
    fun deleteAll() {
        mReminderDao.deleteAll()
    }
    fun resetDb() {
        deleteAndPupulateAsyncTask(mReminderDao).execute()
    }
}

private class insertAsyncTask internal constructor(private val mAsyncTaskDao: ReminderDao) : AsyncTask<Reminder, Void, Void>() {

    override fun doInBackground(vararg params: Reminder): Void? {
        mAsyncTaskDao.insert(params[0])
        return null
    }
}
private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: ReminderDao) : AsyncTask<Reminder, Void, Void>() {

    override fun doInBackground(vararg params: Reminder): Void? {
        mAsyncTaskDao.delete(params[0])
        return null
    }
}

private class deleteAndPupulateAsyncTask internal constructor(private val mDao: ReminderDao) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        mDao.deleteAll()
        var Reminder = Reminder(name="Vasil")
        mDao.insert(Reminder)
        Reminder = Reminder(name="Andriy")
        mDao.insert(Reminder)
        Reminder = Reminder(name="Ivan")
        mDao.insert(Reminder)
        Reminder = Reminder(name="Petro")
        mDao.insert(Reminder)
        Reminder = Reminder(name="Nestor")
        mDao.insert(Reminder)
        return null
    }
}

