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
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import android.content.Context.MODE_PRIVATE
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

enum class UIState{
    INVALID_INPUT,
    CAN_DOWNLOAD,
    DOWNLOADED
}

class FileLoadingViewModel(val app:Application): AndroidViewModel(app){
    val uiState:MutableLiveData<UIState> = MutableLiveData()
    val fileId:MutableLiveData<String> = MutableLiveData()
    fun getFileName()= fileId.value + DOWNLOAD_FILE_EXT
    var downloadManager:DownloadManager?=null
    var fileLocation:String? = ""
    var blockingQueue = LinkedBlockingDeque<Runnable>()
    var executor = ThreadPoolExecutor(1,1,3,TimeUnit.MINUTES,blockingQueue)

    init{
        uiState.value = UIState.CAN_DOWNLOAD;
        fileId.value = ""
        fileId.observeForever( object : Observer<String> {
            override fun onChanged(@Nullable s: String?) {
                val isValid = !TextUtils.isEmpty(s)
                        && TextUtils.indexOf(s,' ') < 0
                        //s?.toBigIntegerOrNull() != null
                if(!isValid)
                    uiState.value = UIState.INVALID_INPUT
                else
                    if(fileExists())
                        uiState.value = UIState.DOWNLOADED
                    else
                        uiState.value = UIState.CAN_DOWNLOAD
            }
        })


    }

    fun fileExists():Boolean{
        try {
            val inp = app.openFileInput(fileId.value + DOWNLOAD_FILE_EXT);
            return true;
        }catch(e:FileNotFoundException){
            return false
        }
    }
    fun checkDownloaded(){
        if(fileExists())
            uiState.value = UIState.DOWNLOADED
        else {
            uiState.value = UIState.CAN_DOWNLOAD
            Log.e("MyERROR","DOWNLOADED FILE NOT FOUND")
        }
    }
    fun download(){
        val task = DownloadFileAsyncTask(this)
        task.executeOnExecutor(executor)
    }
    fun delete(){
        this.app.deleteFile(getFileName())
        uiState.value = UIState.CAN_DOWNLOAD
    }
    fun open(){
//        val file = File(app.filesDir.path + getFileName())
//        var target = Intent(Intent.ACTION_VIEW)
//        target.setDataAndType(Uri.fromFile(file),"application/pdf");
//        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//        val intent = Intent.createChooser(target, "Open File");
//        try {
//            app.applicationContext.startActivity(intent);
//        } catch ( e: ActivityNotFoundException) {
//            showToast("There is no PDF reader")
//        }
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
            if(vm.fileExists())
                this.cancel(true)
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
                        val outp:FileOutputStream =  vm.app.openFileOutput(fileName, MODE_PRIVATE)
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
                Log.d("EXCEPTION",e.toString())
            }
        }
    }
}