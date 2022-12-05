package com.itis.androidcourse.recycler.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidcourse.databinding.ItemSongBinding
import com.itis.androidcourse.model.Song

class SongHolder(
    private val binding: ItemSongBinding,
    private val action: (Song) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var song: Song? = null

    init {
        binding.root.setOnClickListener {
            song?.also(action)
        }
    }

    fun onBind(song: Song) {
        this.song = song
        with(binding) {
            tvName.text = song.name
            tvAuthor.text = song.author
            ivPhoto.setImageResource(song.cover)
            if (song.isPlaying) ivIsPlaying.visibility = View.VISIBLE
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Song) -> Unit,
        ): SongHolder = SongHolder(
            binding = ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action = action,
        )
    }
}