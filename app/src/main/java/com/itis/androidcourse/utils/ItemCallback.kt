package com.itis.androidcourse.utils

import androidx.recyclerview.widget.DiffUtil
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.model.Item

object ItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean = (oldItem as? Dog)?.id == (newItem as? Dog)?.id ||
            (oldItem as? Item.Ad)?.id == (newItem as? Item.Ad)?.id


    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean = oldItem == newItem
}
