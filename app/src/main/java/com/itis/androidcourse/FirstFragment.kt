package com.itis.androidcourse

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.itis.androidcourse.databinding.FragmentFirstBinding
import java.lang.NumberFormatException

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
        binding.run {
            tvCounter.text = "Counter value: $counter"
            btnToSecondFragment.setOnClickListener {
//                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    parentFragmentManager.beginTransaction()
//                        .replace(R.id.container_second, SecondFragment.newInstance(counter), "Tag")
//                        .addToBackStack("ToFirstFragment")
//                        .commit()
//                }
//                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
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
//                }
            }
            btnCounter.setOnClickListener {
                counter++
                tvCounter.text = "Counter value: $counter"
            }
            btnDialog.setOnClickListener {
                CounterDialog.newInstance(childFragmentManager)
//                val view = LayoutInflater.from(requireContext())
//                    .inflate(R.layout.fragment_dialog, null, false)
//                view.run {
//                    val editText = this.findViewById<EditText>(R.id.et_number)
//                    val inputLayout = this.findViewById<TextInputLayout>(R.id.til_text)
//                    val dialog = showDialog(
//                        positiveText = "Сложить",
//                        neutralText = "Вычесть",
//                        negativeText = "Отмена",
//                        view = this,
//                        positiveAction = {
//                            try {
//                                val num = editText.text.toString().toInt()
//                                if (num in 0..100) {
//                                    counter += num
//                                    binding.tvCounter.text = "Counter value: $counter"
//                                    dialog.dismiss()
//                                } else {
//                                    inputLayout.error = "Не верный формат данных"
//                                }
//                            } catch (e: NumberFormatException) {
//                                inputLayout.error = "Не верный формат данных"
//                            }
//                        },
//                        neutralAction = {
//                            try {
//                                val num = editText.text.toString().toInt()
//                                when {
//                                    (num in 0..100 && counter - num >= 0) -> {
//                                        counter -= num
//                                        binding.tvCounter.text = "Counter value: $counter"
//                                        dialog.dismiss()
//                                    }
//                                    (counter - num < 0 && num in 0..100) -> {
//                                        inputLayout.error =
//                                            "Значение счётчика не может быть отрицательным числом"
//                                    }
//                                    else -> {
//                                        inputLayout.error = "Не верный формат данных"
//                                    }
//                                }
//                            } catch (e: NumberFormatException) {
//                                inputLayout.error = "Не верный формат данных"
//                            }
//                        }
//                    )
//                }
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
//
//    private fun showDialog() {
//        val view = LayoutInflater.from(requireContext())
//            .inflate(R.layout.fragment_dialog, null, false)
//        view.run {
//            val editText = this.findViewById<EditText>(R.id.et_number)
//            val inputLayout = this.findViewById<TextInputLayout>(R.id.til_text)
//
//            val dialog = AlertDialog.Builder(requireContext())
//                .setTitle("Сложить или Вычесть?")
//                .setView(view)
//                .setPositiveButton("Сложить", null)
//                .setNegativeButton("Отмена") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .setNeutralButton("Вычесть", null)
//                .show()
//            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            positiveButton.setOnClickListener {
//                try {
//                    val num = editText.text.toString().toInt()
//                    if (num in 0..100) {
//                        counter += num
//                        binding.tvCounter.text = "Counter value: $counter"
//                        dialog.dismiss()
//                    } else {
//                        inputLayout.error = "Не верный формат данных"
//                    }
//                } catch (e: NumberFormatException) {
//                    inputLayout.error = "Не верный формат данных"
//                }
//            }
//            val neutralButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
//            neutralButton.setOnClickListener {
//                try {
//                    val num = editText.text.toString().toInt()
//                    when {
//                        (num in 0..100 && counter - num >= 0) -> {
//                            counter -= num
//                            binding.tvCounter.text = "Counter value: $counter"
//                            dialog.dismiss()
//                        }
//                        (counter - num < 0 && num in 0..100) -> {
//                            inputLayout.error =
//                                "Значение счётчика не может быть отрицательным числом"
//                        }
//                        else -> {
//                            inputLayout.error = "Не верный формат данных"
//                        }
//                    }
//                } catch (e: NumberFormatException) {
//                    inputLayout.error = "Не верный формат данных"
//                }
//            }
//        }
//    }

    companion object {
        private const val ARG_COUNT = "ARG_COUNT"
    }
}