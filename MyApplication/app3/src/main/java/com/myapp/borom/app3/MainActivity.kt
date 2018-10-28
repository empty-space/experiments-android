package com.myapp.borom.app3

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
    private var mGroupmateViewModel: GroupmatesViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        //adapter
        val adapter = GroupmateListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        //observer
        mGroupmateViewModel = ViewModelProviders.of(this).get(GroupmatesViewModel::class.java)
        resetDb()
        mGroupmateViewModel?.allGroupmates?.observe(this, object : Observer<List<Groupmate>> {
            override fun onChanged(@Nullable groupmates: List<Groupmate>?) {
                // Update the cached copy of the groupmates in the adapter.
                groupmates?.let{ adapter.mGroupmates = groupmates}
            }
        })
        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun swipeLeft(position:Groupmate) {
                mGroupmateViewModel!!.delete(position)
//                adapter.mGroupmates.delete(position)
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
        mGroupmateViewModel?.resetDb()
    }

    private fun changeLastItem(){
        mGroupmateViewModel?.modifyLast()
    }
    private fun deleteAll(){
        mGroupmateViewModel?.deleteAll()
    }
    private fun addNew(){
        val intent = Intent(this, NewGroupmate::class.java);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val groupmate = Groupmate(name = data!!.getStringExtra(EXTRA_REPLY))
            mGroupmateViewModel!!.insert(groupmate)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }
}
