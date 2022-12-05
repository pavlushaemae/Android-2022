package com.itis.androidcourse.recycler.utils

import androidx.recyclerview.widget.DiffUtil
import com.itis.androidcourse.model.Song

object ItemCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem == newItem
}