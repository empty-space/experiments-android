package com.myapp.borom.app3

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import android.support.v7.widget.helper.ItemTouchHelper.*

internal class SwipeController(val actions :SwipeControllerActions) : Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val item =(viewHolder as GroupmateListAdapter.GroupmateViewHolder).item
        actions.swipeLeft(item as Groupmate)
    }
}