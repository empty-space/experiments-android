package com.myapp.borom.app6

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData


//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.ViewModel
//import androidx.paging.LivePagedListBuilder
//import androidx.paging.PagedList


class RemindersViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: ReminderRepository

    internal val allReminders: LiveData<List<Reminder>>

    init {
        mRepository = ReminderRepository(application)
        allReminders = mRepository.mAllReminders
    }

    fun insert(Reminder: Reminder) {
        mRepository.insert(Reminder)
    }
    fun update(Reminder: Reminder) {
        mRepository.update(Reminder)
    }
    fun delete(Reminder: Reminder) {
        mRepository.delete(Reminder)
    }
    fun deleteAll() {
        mRepository.deleteAll()
    }
    fun resetDb() {
        mRepository.resetDb()
    }
    fun modifyLast() {
        val item = allReminders.value?.last()
        if (item != null) {
            item.name = "Ivanov Ivan Ivanovich"
            mRepository.update(item)
        }
    }
}

//
//class RemindersViewModel(app: Application): AndroidViewModel(app) {
//
//    private val database = UniversityDatabase.getDatabase(app)
//    private val ReminderDao = database.ReminderDao()
//
//    fun populateLocalDatabasehrases(){
//        MainActivity.Reminders.forEach { it ->
//            it.Reminder = "${it.id} - ${it.Reminder}"
//
//            ReminderDao.insert(it)
//        }
//    }
//
//    val RemindersList: LiveData<PagedList<Reminder>> =
//            LivePagedListBuilder(ReminderDao.allReminders(), 10)
//                    .setBoundaryCallback(object: PagedList.BoundaryCallback<Reminder>(){
//                        override fun onItemAtEndLoaded(itemAtEnd: Reminder) {
//                            Log.i(TAG, "onItemAtEndLoaded")
//
//                            populateLocalDatabasehrases()
//                        }
//
//                        override fun onZeroItemsLoaded() {
//                            Log.i(TAG, "onZeroItemsLoaded")
//
//                            populateLocalDatabasehrases()
//                        }
//
//                        override fun onItemAtFrontLoaded(itemAtFront: Reminder) {
//                            Log.i(TAG, "onItemAtFrontLoaded")
//                        }
//                    })
//                    .build()
//
//    companion object {
//        private const val TAG = "RemindersViewModel"
//    }
//}