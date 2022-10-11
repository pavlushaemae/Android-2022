package com.itis.androidcourse

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

typealias Click = () -> Unit

fun Fragment.showDialog(
    title: String = "",
    view: View? = null,
    message: String = "",
    positiveText: String = "",
    neutralText: String = "",
    negativeText: String = "",
    positiveAction: Click? = null,
    negativeAction: Click? = null,
    neutralAction: Click? = null,
): Dialog? {
    val dialog = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setView(view)
        .setMessage(message)
        .setPositiveButton(positiveText, null)
        .setNegativeButton(negativeText) { dialog, _ ->
            negativeAction?.invoke()
            dialog.dismiss()
        }
        .setNeutralButton(neutralText, null)
        .create()
    val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    positiveButton.setOnClickListener {
        positiveAction?.invoke()
    }
    val neutralButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
    neutralButton.setOnClickListener {
        neutralAction?.invoke()
    }
    return dialog
}