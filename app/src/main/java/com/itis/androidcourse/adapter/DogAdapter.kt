package com.itis.androidcourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidcourse.databinding.ItemDogBinding
import com.itis.androidcourse.model.Dog

class DogAdapter(
    private val list: List<Dog>,
    private val glide: RequestManager,
    private val action: (Dog) -> Unit
) : RecyclerView.Adapter<DogHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogHolder = DogHolder(
        binding = ItemDogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide = glide,
        action = action
    )

    override fun onBindViewHolder(
        holder: DogHolder,
        position: Int
    ) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}