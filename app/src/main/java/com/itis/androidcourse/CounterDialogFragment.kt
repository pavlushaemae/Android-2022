package com.itis.androidcourse

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout

class CounterDialogFragment(private var counter: Int, val onClick: (Int) -> Unit) :
    DialogFragment(R.layout.fragment_dialog) {

    private var viewDialog: View? = null
    private var myDialog: AlertDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewDialog = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog, null, false)

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
        val editText = viewDialog?.findViewById<EditText>(R.id.et_number)
        val inputLayout = viewDialog?.findViewById<TextInputLayout>(R.id.til_text)
        var num = 0
        myDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            try {
                num = editText?.text.toString().toInt()
                if (num in 0..100) {
                    counter += num
                    onClick(counter)
                    myDialog?.dismiss()
                } else {
                    inputLayout?.error = "Не верный формат данных"
                }
            } catch (e: NumberFormatException) {
                inputLayout?.error = "Не верный формат данных"
            }
        }
        myDialog?.getButton(AlertDialog.BUTTON_NEUTRAL)?.setOnClickListener {
            try {
                num = editText?.text.toString().toInt()
                when {
                    (num in 0..100 && counter - num >= 0) -> {
                        counter -= num
                        onClick(counter)
                        println(counter)
                        myDialog?.dismiss()
                    }
                    (counter - num < 0 && num in 0..100) -> {
                        inputLayout?.error =
                            "Значение счётчика не может быть отрицательным числом"
                    }
                    else -> {
                        inputLayout?.error = "Не верный формат данных"
                    }
                }
            } catch (e: NumberFormatException) {
                inputLayout?.error = "Не верный формат данных"
            }
        }
    }
}