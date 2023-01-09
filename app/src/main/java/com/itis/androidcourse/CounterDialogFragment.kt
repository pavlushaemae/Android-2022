package com.itis.androidcourse

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.itis.androidcourse.databinding.FragmentDialogBinding
import com.itis.androidcourse.databinding.FragmentFirstBinding

class CounterDialogFragment() :
    DialogFragment(R.layout.fragment_dialog) {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!
    private var counter: Int? = null
    private var onClick: ((Int) -> Unit)? = null
    private var viewDialog: View? = null
    private var myDialog: AlertDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewDialog = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog, null, false).also {
                _binding = FragmentDialogBinding.bind(it)
            }
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle("Сложить или вычесть?")
            .setView(viewDialog)
            .setPositiveButton("Сложить", null)
            .setNegativeButton("Отменить") { myDialog, _ ->
                myDialog.dismiss()
            }
            .setNeutralButton("Вычесть", null)
            .create()
        return myDialog as Dialog
    }

    override fun onStart() {
        super.onStart()
        var num = 0
        myDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            try {
                num = binding.etNumber.text.toString().toInt()
                if (num in 0..100) {
                    counter?.also { c ->
                        onClick?.invoke(c + num)
                        counter = c + num
                        myDialog?.dismiss()
                    }
                } else {
                    binding.tilText.error = "Не верный формат данных"
                }
            } catch (e: NumberFormatException) {
                binding.tilText.error = "Не верный формат данных"
            }
        }
        myDialog?.getButton(AlertDialog.BUTTON_NEUTRAL)?.setOnClickListener {
            try {
                num = binding.etNumber.text.toString().toInt()
                counter?.let {
                    when {
                        (num in 0..100 && it - num >= 0) -> {
                            onClick?.invoke(it - num)
                            counter = it - num
                            myDialog?.dismiss()
                        }
                        (it - num < 0 && num in 0..100) -> {
                            binding.tilText.error =
                                "Значение счётчика не может быть отрицательным числом"
                        }
                        else -> {
                            binding.tilText.error = "Не верный формат данных"
                        }
                    }
                }

            } catch (e: NumberFormatException) {
                binding.tilText.error = "Не верный формат данных"
            }
        }
    }

    companion object {
        fun newInstance(counter: Int, onClick: (Int) -> Unit): CounterDialogFragment =
            CounterDialogFragment().also {
                it.onClick = onClick
                it.counter = counter
            }

    }
}