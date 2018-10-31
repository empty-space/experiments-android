package com.myapp.borom.app4

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View


internal class SongListAdapter internal constructor(context: Context) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private val mInflater: LayoutInflater
    var mSongs: List<Song>? = null
        set(value){
            field=value;
            notifyDataSetChanged()
        }

    internal inner class SongViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val singerView: TextView = itemView.findViewById(R.id.textSinger)
        val nameView: TextView = itemView.findViewById(R.id.textViewName)
        val dateView: TextView = itemView.findViewById(R.id.textViewDate)
        var item:Song?=null
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        if (mSongs != null) {
            val current = mSongs!![position]
            holder.item=current
            holder.nameView.text = current.song_name
            holder.singerView.text = current.singer_name
            holder.dateView.text = current.date
        } else {
            // Covers the case of data not being ready yet.
            holder.nameView.text = "NOT READY"
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mSongs has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mSongs != null)
            mSongs!!.size
        else
            0
    }
}