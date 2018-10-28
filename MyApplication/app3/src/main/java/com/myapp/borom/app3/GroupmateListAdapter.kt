package com.myapp.borom.app3

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View


internal class GroupmateListAdapter internal constructor(context: Context) : RecyclerView.Adapter<GroupmateListAdapter.GroupmateViewHolder>() {

    private val mInflater: LayoutInflater
    var mGroupmates: List<Groupmate>? = null
        set(value){
            field=value;
            notifyDataSetChanged()
        }

    internal inner class GroupmateViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idView: TextView = itemView.findViewById(R.id.textViewId)
        val nameView: TextView = itemView.findViewById(R.id.textViewName)
        val dateView: TextView = itemView.findViewById(R.id.textViewDate)
        var item:Groupmate?=null
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupmateViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return GroupmateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupmateViewHolder, position: Int) {
        if (mGroupmates != null) {
            val current = mGroupmates!![position]
            holder.item=current
            holder.idView.text = current.id.toString()
            holder.nameView.text = current.name
            holder.dateView.text = current.date
        } else {
            // Covers the case of data not being ready yet.
            holder.idView.text = "NO NAME"
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mGroupmates has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mGroupmates != null)
            mGroupmates!!.size
        else
            0
    }
}