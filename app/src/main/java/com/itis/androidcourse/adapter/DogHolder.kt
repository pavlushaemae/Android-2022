package com.itis.androidcourse.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidcourse.R
import com.itis.androidcourse.databinding.ItemDogBinding
import com.itis.androidcourse.model.Dog

class DogHolder(
    private val binding: ItemDogBinding,
    private val action: (Dog) -> Unit,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    private var dog: Dog? = null

    init {
        binding.root.setOnClickListener {
            dog?.also(action)
        }
    }

    fun onBind(dog: Dog) {
        this.dog = dog
        with(binding) {
            tvTitle.text = dog.name
            tvDesc.text = dog.country

            glide
                .load(dog.photo)
                .apply(option)
                .centerCrop()
                .placeholder(R.drawable.default_dog)
                .error(R.drawable.default_dog)
                .into(ivPhoto)
        }
    }
}