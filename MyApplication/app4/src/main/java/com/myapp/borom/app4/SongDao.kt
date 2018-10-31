package com.myapp.borom.app4

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface SongDao {

    @Query("SELECT * FROM $SONG_TABLE")
    fun getAll(): LiveData<List<Song>>

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("DELETE FROM "+ SONG_TABLE)
    fun deleteAll()
}