package com.itis.androidcourse

import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.View
import com.itis.androidcourse.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getInt(ARG_COUNT)?.run {
            counter = this
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)
        if (arguments != null) {
            counter = arguments?.getInt(SyncStateContract.Constants._COUNT) ?: 0
        }
        binding.run {
            tvCounter.text = "Counter value: $counter"
            btnToSecondFragment.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                    )
                    .replace(R.id.container, SecondFragment.newInstance(counter), "Tag")
                    .addToBackStack("ToFirstFragment")
                    .commit()
            }
            btnCounter.setOnClickListener {
                counter++
                tvCounter.text = "Counter value: $counter"
            }
            btnDialog.setOnClickListener {
                val dialog = CounterDialogFragment.newInstance(counter) { num ->
                    counter = num
                    tvCounter.text = "Counter value: $counter"
                    arguments?.apply {
                        putInt(SyncStateContract.Constants._COUNT, num)
                    }
                }
                dialog.show(parentFragmentManager, "Dialog")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(ARG_COUNT, counter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_COUNT = "ARG_COUNT"
    }
}