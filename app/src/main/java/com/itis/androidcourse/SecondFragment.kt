package com.itis.androidcourse

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.androidcourse.databinding.FragmentSecondBinding


class SecondFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)
        arguments?.getInt(ARG_COUNTER)?.run {
            when (this) {
                in 0..50 -> view.setBackgroundColor(Color.YELLOW)
                in 51..100 -> view.setBackgroundColor(Color.GREEN)
                else -> view.setBackgroundColor(Color.BLUE)
            }
            binding.tvCounter.text = "Counter value: $this"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val ARG_COUNTER = "ARG_COUNTER"

        fun newInstance(counter: Int) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COUNTER, counter)
                }
            }
    }
}