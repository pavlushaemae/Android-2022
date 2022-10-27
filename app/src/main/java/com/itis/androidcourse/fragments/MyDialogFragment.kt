package com.itis.androidcourse.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.itis.androidcourse.R
import com.itis.androidcourse.model.Dog
import com.itis.androidcourse.repository.DogRepository
import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException

class MyDialogFragment(val onClick: (String, String, Int) -> Unit) :
    DialogFragment(R.layout.fragment_dialog) {

    private var viewDialog: View? = null
    private var myDialog: AlertDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewDialog = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_dialog, null, false)

        myDialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить в список")
            .setView(viewDialog)
            .setPositiveButton("Добавить", null)
            .setNegativeButton("Отменить") { myDialog, _ ->
                myDialog.dismiss()
            }
            .create()
        return myDialog as Dialog
    }

    override fun onStart() {
        super.onStart()

        myDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            val title = viewDialog?.findViewById<EditText>(R.id.et_title)?.text.toString()
            val description =
                viewDialog?.findViewById<EditText>(R.id.et_description)?.text.toString()
            val position = viewDialog?.findViewById<EditText>(R.id.et_position)?.text.toString()
            var index = 0
            index = try {
                when {
                    position.toInt() == 0 -> {
                        0
                    }
                    DogRepository.items[position.toInt() - 1] is Dog -> {
                        val dog = DogRepository.items[position.toInt() - 1]
                        DogRepository.listOfDog.indexOf(dog)
                    }
                    else -> {
                        val dog = DogRepository.items[position.toInt()]
                        DogRepository.listOfDog.indexOf(dog)
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                DogRepository.listOfDog.size
            } catch (e: NumberFormatException) {
                DogRepository.listOfDog.size
            }
            onClick(title, description, index)
            myDialog?.dismiss()
        }
    }
}