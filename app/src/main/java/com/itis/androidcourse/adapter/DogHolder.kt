package com.itis.androidcourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidcourse.R
import com.itis.androidcourse.databinding.ItemDogBinding
import com.itis.androidcourse.helper.SwipeToDeleteCallback
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.model.Item
import com.itis.androidcourse.repository.DogRepository

class DogHolder(
    private val binding: ItemDogBinding,
    private val action: (Dog) -> Unit,
    private val actionDelete: (Dog) -> Unit,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    private var dog: Dog? = null

    init {
        binding.root.setOnClickListener {
            dog?.also(action)
        }
        binding.ivDelete.setOnClickListener {
            dog?.also(actionDelete)
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
    companion object {
        const val ARG_NAME = "arg_name"

        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            action: (Dog) -> Unit,
            actionDelete: (Dog) -> Unit,
        ): DogHolder = DogHolder(
            binding = ItemDogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            action = action,
            actionDelete = actionDelete,
        )
    }
}