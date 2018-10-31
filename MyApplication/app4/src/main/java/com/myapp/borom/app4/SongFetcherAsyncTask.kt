package com.myapp.borom.app4


import android.os.AsyncTask
import okhttp3.*
import java.lang.Exception
import java.net.UnknownHostException

const val api_login    ="4707login"
const val api_password  = "4707pass"
const val api_radio_url = "http://media.ifmo.ru/api_get_current_song.php"

class SongFetcherAsyncTask internal constructor(private val songsViewModel: SongsViewModel, val onError:(String)->Unit) : AsyncTask<Void, Void, Void>() {

    var error:String?=null
    var song:Song?=null

    fun reset(){
        error=null
        song=null
    }
    override fun doInBackground(vararg params: Void?): Void? {
            reset()
            try {
                //song = getCurrentSong()
                song = getCurSong()
            }
            catch (ex:UnknownHostException){
                error = "Check your connection to Internet"
            }
            catch (ex:Exception){
                error = ex.toString()
            }
        return null;
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if(song!=null) {
            songsViewModel.insert(song as Song)
            onError("Successfully updated")
        }
        else
            onError(error as String)
    }

    fun getCurSong():Song{
        val client = OkHttpClient();
        val body = FormBody.Builder()
                .add("login", api_login)
                .add("password", api_password)
                .build()//.create(JSON, data);
        val request = Request.Builder()
                .url(api_radio_url)
                .post(body)
                .build();
        val response = client.newCall(request).execute();
        val stringResponse = response.body()?.string()
        return parseSong(stringResponse)
    }
    fun parseSong(str:String?):Song{
        if(str==null)
            throw Exception("Empty response")
        //fetch song
        val jsonObject = parseToResponse(str)
        if (jsonObject.result == "success") {
            return Song(song_name = jsonObject.getSongName(),singer_name = jsonObject.getSingerName())
        }
        else
            throw Exception("Error response")
    }
    fun parseToResponse(stringResponse:String?):Response{
        val gson = com.google.gson.Gson()
        val result = gson.fromJson(stringResponse,Response::class.java)
        return result
    }


    class Response {
        lateinit var info: String
        lateinit var result: String
        fun getSingerName() = info.substringBefore('-').trim(' ')
        fun getSongName() = info.substringAfter('-').trim(' ')
    }

}