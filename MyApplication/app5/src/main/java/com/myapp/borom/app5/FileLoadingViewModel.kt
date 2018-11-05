package com.myapp.borom.app5

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.annotation.Nullable
import android.text.TextUtils
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.app.DownloadManager
import android.arch.lifecycle.AndroidViewModel
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.*
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import android.content.Intent
import android.content.res.Resources
import android.os.AsyncTask
import android.widget.Toast
import java.io.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


const val DOWNLOAD_FILE_URL = "http://ntv.ifmo.ru/file/article/"
const val DOWNLOAD_FILE_EXT =".pdf"
const val DOWNLOADS_FILES_FOLDER = "pdfs"

enum class UIState{
    INVALID_INPUT,
    CAN_DOWNLOAD,
    DOWNLOADED
}

class FileLoadingViewModel(val app:Application): AndroidViewModel(app){
    val uiState:MutableLiveData<UIState> = MutableLiveData()
    val fileId:MutableLiveData<String> = MutableLiveData()
    var blockingQueue = LinkedBlockingDeque<Runnable>()
    var executor = ThreadPoolExecutor(1,1,3,TimeUnit.MINUTES,blockingQueue)

    fun getFileName()= fileId.value + DOWNLOAD_FILE_EXT
    fun getFileAbsolutePath()= app.applicationContext.getExternalFilesDir(DOWNLOADS_FILES_FOLDER).absolutePath + "/" + getFileName()

    init{
        fileId.observeForever( object : Observer<String> {
            override fun onChanged(@Nullable s: String?) {
                if(!isValidId(s))
                    uiState.value = UIState.INVALID_INPUT
                else
                    if(fileExists())
                        uiState.value = UIState.DOWNLOADED
                    else
                        uiState.value = UIState.CAN_DOWNLOAD
            }
        })
        fileId.value = ""
    }

    fun isValidId(s:String? = fileId.value):Boolean{
        return !TextUtils.isEmpty(s)
                && TextUtils.indexOf(s,' ') < 0
        //s?.toBigIntegerOrNull() != null
    }
    fun fileExists():Boolean{
        try {
            return File(getFileAbsolutePath()).exists();
        }catch(e:FileNotFoundException){
            return false
        }
    }
    fun checkDownloaded(){
        if(isValidId()&& fileExists())
            uiState.value = UIState.DOWNLOADED
        else {
            uiState.value = UIState.CAN_DOWNLOAD
            Log.e("MyERROR","DOWNLOADED FILE NOT FOUND ${getFileAbsolutePath()}")
        }
    }

    fun download(){
        val task = DownloadFileAsyncTask(this)
        task.executeOnExecutor(executor)
    }
    fun delete(){
        Log.d("TRY DELETE",getFileAbsolutePath())
        val deleted = File(getFileAbsolutePath()).delete()
        if(!deleted)
            showToast("Not deleted")
        showToast("Deleted succesfully")
        checkDownloaded()
    }
    fun showToast(message:String){
        Toast.makeText(
                app.applicationContext,
                message,
                Toast.LENGTH_SHORT)
                .show()
    }

    class DownloadFileAsyncTask internal constructor(private val vm:FileLoadingViewModel) : AsyncTask<Void, Void, Void>() {
        var noSuchFileOnServer = false

        override fun onPreExecute() {
            if(vm.fileExists()){
                this.cancel(true)
                vm.showToast("Already downloaded ${vm.getFileName()}")
            }
            else
                vm.showToast("Downloading ${vm.getFileName()}")
        }

        override fun doInBackground(vararg params: Void?): Void? {
            dowloadIt()
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if(noSuchFileOnServer)
                Toast.makeText(vm.app.applicationContext,"No such file on server",Toast.LENGTH_SHORT)
                    .show()
            else {
                if(vm.fileExists())
                    vm.showToast("Downloaded")
                vm.checkDownloaded()
            }
        }
        fun dowloadIt(){
            val fileName = vm.getFileName()
            val fileUrl = DOWNLOAD_FILE_URL + fileName
            val url = URL(fileUrl)
            try {
                var httpConnection = url.openConnection() as HttpURLConnection
                val answerType = httpConnection.getHeaderField("Content-Type")
                when (answerType) {
                    "application/pdf" -> {
                        //DOWNLOAD
                        val inp = BufferedInputStream(httpConnection.inputStream)
                        var outp:FileOutputStream = FileOutputStream(vm.getFileAbsolutePath())

// пишем данные
                        val dataBuffer = ByteArray(1024)
                        var bytesRead: Int = 0
                        while (bytesRead != -1) {
                            bytesRead = inp.read(dataBuffer, 0, 1024)
                            outp.write(dataBuffer, 0, bytesRead)
                        }
// закрываем поток
                        inp.close()
                        outp.close()
                    }
                    else -> {
                        noSuchFileOnServer = true
                    }
                }
            }
            catch (e:Exception){
                Log.e("Error",e.toString())
            }
        }
    }
}