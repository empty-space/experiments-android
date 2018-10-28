package com.myapp.borom.app3

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData


//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.ViewModel
//import androidx.paging.LivePagedListBuilder
//import androidx.paging.PagedList


class GroupmatesViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: GroupmateRepository

    internal val allGroupmates: LiveData<List<Groupmate>>

    init {
        mRepository = GroupmateRepository(application)
        allGroupmates = mRepository.mAllGroupmates
    }

    fun insert(groupmate: Groupmate) {
        mRepository.insert(groupmate)
    }
    fun update(groupmate: Groupmate) {
        mRepository.update(groupmate)
    }
    fun delete(groupmate: Groupmate) {
        mRepository.delete(groupmate)
    }
    fun deleteAll() {
        mRepository.deleteAll()
    }
    fun resetDb() {
        mRepository.resetDb()
    }
    fun modifyLast() {
        val item = allGroupmates.value?.last()
        if (item != null) {
            item.name = "Ivanov Ivan Ivanovich"
            mRepository.update(item)
        }
    }
}

//
//class GroupmatesViewModel(app: Application): AndroidViewModel(app) {
//
//    private val database = UniversityDatabase.getDatabase(app)
//    private val groupMateDao = database.groupmateDao()
//
//    fun populateLocalDatabasehrases(){
//        MainActivity.groupmates.forEach { it ->
//            it.groupMate = "${it.id} - ${it.groupMate}"
//
//            groupMateDao.insert(it)
//        }
//    }
//
//    val groupMatesList: LiveData<PagedList<Groupmate>> =
//            LivePagedListBuilder(groupMateDao.allGroupMates(), 10)
//                    .setBoundaryCallback(object: PagedList.BoundaryCallback<Groupmate>(){
//                        override fun onItemAtEndLoaded(itemAtEnd: Groupmate) {
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
//                        override fun onItemAtFrontLoaded(itemAtFront: Groupmate) {
//                            Log.i(TAG, "onItemAtFrontLoaded")
//                        }
//                    })
//                    .build()
//
//    companion object {
//        private const val TAG = "GroupmatesViewModel"
//    }
//}