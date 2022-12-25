package com.itis.androidcourse.recycler.utils

import androidx.recyclerview.widget.DiffUtil
import com.itis.androidcourse.data.entity.Note

object ItemCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
}