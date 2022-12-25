package com.itis.androidcourse.recycler.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidcourse.R
import com.itis.androidcourse.data.entity.Note

class NoteHolder(
    private val view: View,
    private val action: (Note) -> Unit,
    private val actionDelete: (Note) -> Unit
) : RecyclerView.ViewHolder(view) {
    private var tvTitle: TextView? = null
    private var tvDescription: TextView? = null
    private var ivDelete: ImageView? = null
    private var note: Note? = null

    init {
        tvTitle = view.findViewById(R.id.tv_title)
        tvDescription = view.findViewById(R.id.tv_description)
        ivDelete = view.findViewById(R.id.iv_delete)
        view.setOnClickListener {
            note?.also(action)
        }
        ivDelete?.setOnClickListener {
            note?.also(actionDelete)
        }
    }



    fun onBind(note: Note) {
        this.note = note
            tvTitle?.text = note.title
            tvDescription?.text = note.description
    }

    companion object {
        fun create(
            action: (Note) -> Unit,
            actionDelete: (Note) -> Unit,
            view: View,
        ): NoteHolder = NoteHolder(
            view = view,
            action = action,
            actionDelete = actionDelete,
        )
    }
}