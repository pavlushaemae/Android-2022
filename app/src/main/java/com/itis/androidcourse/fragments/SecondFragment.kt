package com.itis.androidcourse.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidcourse.R
import com.itis.androidcourse.databinding.FragmentSecondBinding
import com.itis.androidcourse.repository.DogRepository

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)

        val id: Int = arguments?.run {
            getInt(ARG_ID)
        } ?: 0
        val dog = DogRepository.getDogById(id.toLong())
        val glide = Glide.with(this)
        with(binding) {
            tvId.text = "Id: $id"
            glide.run {
                load(dog?.photo)
                    .apply(option)
                    .placeholder(R.drawable.default_dog)
                    .error(R.drawable.default_dog)
                    .into(ivToolbar)
            }
            tvCountry.text = "Страна: ${dog?.country}"
            tvDescription.text = "Описание: ${dog?.description}"
        }

    }

    companion object {

        private const val ARG_ID = "id_arg"

        fun newInstance(idOfDog: Int) = SecondFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, idOfDog)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}