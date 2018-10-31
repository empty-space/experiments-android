package com.myapp.borom.app4

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.support.annotation.Nullable
import android.widget.Toast
import android.os.Handler
import android.support.v7.widget.helper.ItemTouchHelper


const val UPDATE_SONG_INTERVAL:Long = 20 //seconds

class MainActivity : AppCompatActivity() {
    private var mSongViewModel: SongsViewModel?= null
    private var h: Handler = Handler()
    private lateinit var infiniteR:Runnable;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        //adapter
        val adapter = SongListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        //observer
        mSongViewModel = ViewModelProviders.of(this).get(SongsViewModel::class.java)
        mSongViewModel?.allSongs?.observe(this, object : Observer<List<Song>> {
            override fun onChanged(@Nullable songs: List<Song>?) {
                // Update the cached copy of the songs in the adapter.
                songs?.let{ adapter.mSongs = songs}
            }
        })
        //swipe
        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun swipeLeft(position:Song) {
                mSongViewModel!!.delete(position)
            }
        })
        val itemTouchhelper = ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerview);
        //handler //song fetching

        infiniteR = Runnable {
            createTask().execute();
            h.postDelayed(this.infiniteR,UPDATE_SONG_INTERVAL*1000)}
        h.post(infiniteR)
    }
    fun createTask():SongFetcherAsyncTask =
            SongFetcherAsyncTask(mSongViewModel as SongsViewModel, onError = { x: String? -> run {
                showToastError(x)
            }})

    fun showToastError(message:String?):Unit{
        Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT)
            .show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> deleteAll()
            R.id.action_reset_db -> resetDb()
//            R.id.action_settings -> {}
            else -> {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
            }
        }
        return true;
    }

    private fun resetDb(){
        mSongViewModel?.resetDb()
    }
    private fun deleteAll(){
        mSongViewModel?.deleteAll()
    }
}
