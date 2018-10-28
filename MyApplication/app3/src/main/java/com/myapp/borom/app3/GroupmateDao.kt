package com.myapp.borom.app3

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface GroupmateDao {
//    @Insert(onConflict = REPLACE)
//    fun save(Groupmate mate);
//    @Query("SELECT * FROM user WHERE id = :userId")
//    LiveData<User> load(int groupmateId);


    @Query("SELECT * FROM $GROUPMATE_TABLE")
    fun getAll(): LiveData<List<Groupmate>>

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insert(groupmate: Groupmate)

    @Update
    fun update(groupmate: Groupmate)

    @Delete
    fun delete(groupmate: Groupmate)

    @Query("DELETE FROM "+ GROUPMATE_TABLE)
    fun deleteAll()
}