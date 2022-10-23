package com.itis.androidcourse.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidcourse.R
import com.itis.androidcourse.helper.SwipeToDeleteCallback
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.model.Item
import com.itis.androidcourse.utils.ItemCallback

class DogAdapter(
    private val glide: RequestManager,
    private val action: (Dog) -> Unit,
    private val actionDelete: (Dog) -> Unit,
) : ListAdapter<Item, RecyclerView.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_dog -> DogHolder.create(parent, glide, action, actionDelete)
            R.layout.item_ad -> AdHolder.create(parent)
            else -> throw IllegalStateException("Don't implement view type")
        }


//    override fun getItemId(position: Int): Long {
//        return when (val item = getItem(position)) {
//            is Dog -> item.id
//            is Item.Ad -> super.getItemId(position)
//        }
//    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.Ad -> R.layout.item_ad
            is Dog -> R.layout.item_dog
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is Item.Ad -> (holder as AdHolder)
            is Dog -> (holder as DogHolder).onBind(item)
        }
    }

//    override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
////        } else {
////            when (val item = list[position]) {
////                is Item.Ad -> (holder as AdHolder).onBind(title = item.title)
////                is Item.Dog -> (holder as DogHolder).updateFromBundle(payloads.last() as? Bundle)
////            }
//        }
//    }
}