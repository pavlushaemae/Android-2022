package com.itis.androidcourse.recycler.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidcourse.R
import com.itis.androidcourse.data.entity.Note
import com.itis.androidcourse.recycler.holder.NoteHolder
import com.itis.androidcourse.recycler.utils.ItemCallback


class NoteAdapter(
    private val isLinear: Boolean,
    private val action: (Note) -> Unit,
    private val actionDelete: (Note) -> Unit,
) : ListAdapter<Note, RecyclerView.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            if (isLinear) R.layout.item_note_linear else R.layout.item_note_grid,
            null
        )
        return NoteHolder.create(action, actionDelete, v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as NoteHolder).onBind(getItem(position))
}