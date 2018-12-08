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
        resetDb()
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
            R.id.action_change_last -> changeLastItem()
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

    private fun changeLastItem(){
        mReminderViewModel?.modifyLast()
    }
    private fun deleteAll(){
        mReminderViewModel?.deleteAll()
    }
    private fun addNew(){
        val intent = Intent(this, NewReminder::class.java);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val Reminder = Reminder(name = data!!.getStringExtra(EXTRA_REPLY))
            mReminderViewModel!!.insert(Reminder)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }
}
