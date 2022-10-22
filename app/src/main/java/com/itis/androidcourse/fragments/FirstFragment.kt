package com.itis.androidcourse.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.itis.androidcourse.R
import com.itis.androidcourse.adapter.DogAdapter
import com.itis.androidcourse.adapter.SpaceItemDecorator
import com.itis.androidcourse.databinding.FragmentFirstBinding
import com.itis.androidcourse.repository.DogRepository
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.AnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var adapter: DogAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)
        binding.run {
            val itemDecoration =
                SpaceItemDecorator(
                    this@FirstFragment.requireContext(),
                    16.0f
                )
            adapter = DogAdapter(
                DogRepository.listOfDog,
                glide = Glide.with(this@FirstFragment)
            ) {
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        androidx.appcompat.R.anim.abc_fade_in,
                        androidx.appcompat.R.anim.abc_fade_out,
                        androidx.appcompat.R.anim.abc_fade_in,
                        androidx.appcompat.R.anim.abc_fade_out
                    )
                    .replace(R.id.container, SecondFragment.newInstance(it.id), "ToSecond")
                    .addToBackStack("BackToFirst")
                    .commit()
            }
            adapter?.let {
                rvDog.adapter = ScaleInAnimationAdapter(it)
            }
            rvDog.addItemDecoration(itemDecoration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}