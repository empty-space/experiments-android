package com.myapp.borom.app6

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ReminderDao {
//    @Insert(onConflict = REPLACE)
//    fun save(Reminder mate);
//    @Query("SELECT * FROM user WHERE id = :userId")
//    LiveData<User> load(int ReminderId);


    @Query("SELECT * FROM $Reminder_TABLE")
    fun getAll(): LiveData<List<Reminder>>

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insert(Reminder: Reminder)

    @Update
    fun update(Reminder: Reminder)

    @Delete
    fun delete(Reminder: Reminder)

    @Query("DELETE FROM $Reminder_TABLE")
    fun deleteAll()
}