package com.itis.androidcourse

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.itis.androidcourse.databinding.FragmentFirstBinding
import java.lang.NumberFormatException

class CounterDialog : DialogFragment(R.layout.fragment_dialog) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var counter = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        savedInstanceState?.getInt("ARG_COUNT")?.let {
//            counter = it
//        }
        var num = 0
        val editText = view?.findViewById<EditText>(R.id.et_number)
        val inputLayout = view?.findViewById<TextInputLayout>(R.id.til_text)
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog, null, false)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Сложить или вычесть?")
            .setView(view)
            .setPositiveButton("Сложить", null)
            .setNegativeButton("Отменить") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Вычесть", null)
            .create()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            try {
                num = editText?.text.toString().toInt()
                if (num in 0..100) {
                    counter += num

                } else {
                    inputLayout?.error = "Не верный формат данных"
                }
                val intent = Intent().putExtra(ARG_NUM, counter)

                dismiss()
            } catch (e: NumberFormatException) {
                inputLayout?.error = "Не верный формат данных"
            }
        }
        val neutralButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        neutralButton.setOnClickListener {
            try {
                num = editText?.text.toString().toInt()
                when {
                    (num in 0..100 && counter - num >= 0) -> {
                        counter -= num

                    }
                    (counter - num < 0 && num in 0..100) -> {
                        inputLayout?.error =
                            "Значение счётчика не может быть отрицательным числом"
                    }
                    else -> {
                        inputLayout?.error = "Не верный формат данных"
                    }
                }
                val intent = Intent().putExtra(ARG_NUM, counter)
            } catch (e: NumberFormatException) {
                inputLayout?.error = "Не верный формат данных"
            }

        }

        return dialog
//        activity?.let {
//            val builder = AlertDialog.Builder(it)
//                .setMessage("Сложить или вычесть?")
//                .setView(view)
//                .setPositiveButton("Сложить") { dialog, _ ->
//                    try {
//                        num = editText?.text.toString().toInt()
//                    } catch (e: NumberFormatException) {
//                        inputLayout?.error = "Не верный формат данных"
//                    }
//
//                    if (num in 0..100) {
//                        counter += num
//
//                    } else {
//                        inputLayout?.error = "Не верный формат данных"
//                    }
//                }
//                .setNegativeButton("Отмена") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .setNeutralButton("Вычесть") { dialog, _ ->
//                    try {
//                        num = editText?.text.toString().toInt()
//                    } catch (e: NumberFormatException) {
//                        inputLayout?.error = "Не верный формат данных"
//                    }
//                    when {
//                        (num in 0..100 && counter - num >= 0) -> {
//                            counter -= num
//
//                        }
//                        (counter - num < 0 && num in 0..100) -> {
//                            inputLayout?.error =
//                                "Значение счётчика не может быть отрицательным числом"
//                        }
//                        else -> {
//                            inputLayout?.error = "Не верный формат данных"
//                        }
//                    }
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_NUM, counter)
    }

    companion object {
        private const val ARG_NUM = "ARG_NUM"

        fun newInstance(fragmentManager: FragmentManager) {
            return CounterDialog().show(fragmentManager, "Tag")
        }
    }
}
