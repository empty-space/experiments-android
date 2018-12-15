package com.myapp.borom.app6

import android.app.Activity
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.Nullable
import android.widget.Toast
import android.content.Intent
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.myapp.borom.app6.utils.AlarmUtil


val NEW_WORD_ACTIVITY_REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private var mReminderViewModel: RemindersViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        //adapter
        val adapter = ReminderListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        //observer
        mReminderViewModel = ViewModelProviders.of(this).get(RemindersViewModel::class.java)
        //resetDb()
        mReminderViewModel?.allReminders?.observe(this, object : Observer<List<Reminder>> {
            override fun onChanged(@Nullable Reminders: List<Reminder>?) {
                // Update the cached copy of the Reminders in the adapter.
                Reminders?.let{ adapter.mReminders = Reminders}
            }
        })
        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun swipeLeft(position:Reminder) {
                mReminderViewModel!!.delete(position)
//                adapter.mReminders.delete(position)
//                adapter.notifyItemRemoved(position)
//                adapter.notifyItemRangeChanged(position, mAdapter.getItemCount())
            }
        })
        val itemTouchhelper = ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerview);
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        when (item.itemId) {
            R.id.action_add -> addNew()
            R.id.action_delete_all -> deleteAll()
            R.id.action_notification -> notification()
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
        mReminderViewModel?.resetDb()
    }

    private fun notification(){
        val intent = Intent(this, TestNotificationsActivity::class.java);
        startActivity(intent)
    }
    private fun deleteAll(){
        mReminderViewModel?.deleteAll()
    }
    private fun addNew(){
        val intent = Intent(this, NewReminder::class.java);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }
    public fun onFloatingButtonClick(v:View){
       addNew()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                val r = data!!.getSerializableExtra(EXTRA_REPLY) as Reminder
                mReminderViewModel!!.insert(r)
                AlarmUtil.setAlarm(this,r.date!!,r.name)
            }  catch (e:Exception){
                showToast(e.message ?: e.toString())
            }

        } else {
            showToast(R.string.empty_not_saved)
        }
    }
    fun showToast(message:String){
        Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_LONG).show()
    }
    fun showToast(resource:Int){
        Toast.makeText(
                applicationContext,
                resource,
                Toast.LENGTH_LONG).show()
    }
}
