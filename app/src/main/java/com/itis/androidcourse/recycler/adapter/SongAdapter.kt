package com.itis.androidcourse.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidcourse.model.Song
import com.itis.androidcourse.recycler.holder.SongHolder
import com.itis.androidcourse.recycler.utils.ItemCallback

class SongAdapter(
    private val action: (Song) -> Unit,
) : ListAdapter<Song, RecyclerView.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SongHolder.create(parent, action)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as SongHolder).onBind(getItem(position))
}