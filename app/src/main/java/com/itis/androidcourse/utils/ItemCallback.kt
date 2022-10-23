package com.itis.androidcourse.utils

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.itis.androidcourse.adapter.DogHolder
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.model.Item

object ItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean = (oldItem as? Dog)?.id == (newItem as? Dog)?.id


    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean = oldItem == newItem

//    override fun getChangePayload(
//        oldItem: Item,
//        newItem: Item
//    ): Any? {
//        val bundle = Bundle()
//        when {
//            oldItem is Item.Dog && newItem is Item.Dog -> {
//                if (oldItem.name != newItem.name) {
//                    bundle.putString(DogHolder.ARG_NAME, newItem.name)
//                }
//            }
//        }
//        return if (bundle.isEmpty) super.getChangePayload(oldItem, newItem) else bundle
//    }
}
