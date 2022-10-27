package com.itis.androidcourse.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itis.androidcourse.R
import com.itis.androidcourse.adapter.DogAdapter
import com.itis.androidcourse.adapter.SpaceItemDecorator
import com.itis.androidcourse.databinding.FragmentFirstBinding
import com.itis.androidcourse.helper.SwipeToDeleteCallback
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.repository.DogRepository
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

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
                glide = Glide.with(this@FirstFragment),
                {
                    parentFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(
                            androidx.appcompat.R.anim.abc_fade_in,
                            androidx.appcompat.R.anim.abc_fade_out,
                            androidx.appcompat.R.anim.abc_fade_in,
                            androidx.appcompat.R.anim.abc_fade_out
                        )
                        .replace(
                            R.id.container,
                            SecondFragment.newInstance(it.id.toInt()),
                            "ToSecond"
                        )
                        .addToBackStack("BackToFirst")
                        .commit()

                },
                {
                    onDeleteClick(it)
                })
            adapter?.let {
                rvDog.adapter = ScaleInAnimationAdapter(it)
            }


            val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    DogRepository.listOfDog.remove(DogRepository.items[pos])
                    val currentList = DogRepository.items
                    adapter?.submitList(currentList)
                }
            }

            val helper = ItemTouchHelper(swipeToDeleteCallback)
            helper.attachToRecyclerView(rvDog)

            rvDog.addItemDecoration(itemDecoration)
            adapter?.submitList(DogRepository.items)

            btnFloating.setOnClickListener {
                val dialog = MyDialogFragment { title, desc, position ->
                    DogRepository.listOfDog.add(
                        position,
                        Dog((DogRepository.listOfDog.size).toLong(), title, desc, "", "")
                    )
                    val currentList = DogRepository.items
                    adapter?.submitList(currentList) {
                        rvDog.scrollToPosition(position)
                    }
                }
                dialog.show(parentFragmentManager, "Dialog")
            }
        }
    }

    private fun onDeleteClick(dog: Dog) {
        DogRepository.listOfDog.remove(dog)
        val currentList = DogRepository.items
        adapter?.submitList(currentList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}