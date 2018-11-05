package com.myapp.borom.app5

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

enum class FileStates{
    INVALID_ID, //if there is no such file on server
    DOWNLOADING, //if exists file ID.downloading AND it's locked
    DOWNLOADED  // if exists file ID
}

class LoadFileAsyncTask(val fileId:String) : AsyncTask<Void, Void, Void>(){
    var state:String=""
    override fun doInBackground(vararg params: Void?): Void? {
        var fileExist=false;
        var downloadingFileExist=false;
//        if (fileExist) {
//            state=
//        }
        return null;
    }
    //checks downloading file
    fun isTempFileLocked():Boolean{
        try {
// открываем поток для чтения
//            val br = BufferedReader(InputStreamReader( openFileInput(fileId)));
//            var str:String  = "";
//// читаем содержимое
//            while ((str = br.readLine()) != null) { Log.d(LOG_TAG, str);
//            }
        }
        catch (e: FileNotFoundException) {
            return false;
        }
        catch (e: IOException) {
            return true;
        }
        return  false;
    }

}