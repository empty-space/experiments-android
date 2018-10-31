package com.myapp.borom.app4

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class SongsViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: SongRepository

    internal val allSongs: LiveData<List<Song>>

    init {
        mRepository = SongRepository(application)
        allSongs = mRepository.mAllSongs
    }

    fun insert(song: Song) {
        mRepository.insert(song)
    }
    fun update(song: Song) {
        mRepository.update(song)
    }
    fun delete(song: Song) {
        mRepository.delete(song)
    }
    fun deleteAll() {
        mRepository.deleteAll()
    }
    fun resetDb() {
        mRepository.resetDb()
    }
}