package com.myapp.borom.app4

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.os.AsyncTask.execute


class SongRepository(val application: Application){
    val mSongDao: SongDao
    val mAllSongs: LiveData<List<Song>>

    init{
        val db = UniversityDatabase.getDatabase(application);
        mSongDao = db.songDao();
        mAllSongs = mSongDao.getAll();
    }

    fun insert(song: Song) {
        val isCurrent = mAllSongs.value != null
                && mAllSongs.value!!.isNotEmpty()
                && mAllSongs.value!!.last().song_name==song.song_name
        if(!isCurrent)
            insertAsyncTask(mSongDao).execute(song)
    }
    fun update(song: Song) {
        mSongDao.update(song)
    }
    fun delete(song: Song) {
        deleteAsyncTask(mSongDao).execute(song)
    }
    fun deleteAll() {
        mSongDao.deleteAll()
    }
    fun resetDb() {
        deleteAll()
    }
}

private class insertAsyncTask internal constructor(private val mAsyncTaskDao: SongDao) : AsyncTask<Song, Void, Void>() {

    override fun doInBackground(vararg params: Song): Void? {
        mAsyncTaskDao.insert(params[0])
        return null
    }
}
private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: SongDao) : AsyncTask<Song, Void, Void>() {

    override fun doInBackground(vararg params: Song): Void? {
        mAsyncTaskDao.delete(params[0])
        return null
    }
}


