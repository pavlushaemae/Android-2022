package com.itis.androidcourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidcourse.databinding.ItemAdBinding

class AdHolder(
    private val binding: ItemAdBinding
): RecyclerView.ViewHolder(binding.root) {
//    fun onBind(title: String) {
////        binding.root.text = title
//    }

    companion object {

        fun create(parent: ViewGroup): AdHolder = AdHolder(
            binding = ItemAdBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}