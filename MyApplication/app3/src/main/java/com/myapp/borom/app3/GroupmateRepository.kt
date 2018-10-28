package com.myapp.borom.app3

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.os.AsyncTask.execute





class GroupmateRepository(val application: Application){
    val mGroupmateDao: GroupmateDao
    val mAllGroupmates: LiveData<List<Groupmate>>

    init{
        val db = UniversityDatabase.getDatabase(application);
        mGroupmateDao = db.groupmateDao();
        mAllGroupmates = mGroupmateDao.getAll();
    }

    fun insert(groupmate: Groupmate) {
        insertAsyncTask(mGroupmateDao).execute(groupmate)
    }
    fun update(groupmate: Groupmate) {
        mGroupmateDao.update(groupmate)
    }
    fun delete(groupmate: Groupmate) {
        deleteAsyncTask(mGroupmateDao).execute(groupmate)
    }
    fun deleteAll() {
        mGroupmateDao.deleteAll()
    }
    fun resetDb() {
        deleteAndPupulateAsyncTask(mGroupmateDao).execute()
    }
}

private class insertAsyncTask internal constructor(private val mAsyncTaskDao: GroupmateDao) : AsyncTask<Groupmate, Void, Void>() {

    override fun doInBackground(vararg params: Groupmate): Void? {
        mAsyncTaskDao.insert(params[0])
        return null
    }
}
private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: GroupmateDao) : AsyncTask<Groupmate, Void, Void>() {

    override fun doInBackground(vararg params: Groupmate): Void? {
        mAsyncTaskDao.delete(params[0])
        return null
    }
}

private class deleteAndPupulateAsyncTask internal constructor(private val mDao: GroupmateDao) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        mDao.deleteAll()
        var groupmate = Groupmate(name="Vasil")
        mDao.insert(groupmate)
        groupmate = Groupmate(name="Andriy")
        mDao.insert(groupmate)
        groupmate = Groupmate(name="Ivan")
        mDao.insert(groupmate)
        groupmate = Groupmate(name="Petro")
        mDao.insert(groupmate)
        groupmate = Groupmate(name="Nestor")
        mDao.insert(groupmate)
        return null
    }
}

