package com.itis.androidcourse.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.androidcourse.R
import com.itis.androidcourse.databinding.FragmentListBinding
import com.itis.androidcourse.recycler.adapter.SongAdapter
import com.itis.androidcourse.recycler.adapter.SpaceItemDecorator
import com.itis.androidcourse.service.SongService
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var songService: SongService? = null
    private var adapter: SongAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        songService = SongService()
        val itemDecoration =
            SpaceItemDecorator(
                this.requireContext(),
                16.0f
            )

        adapter = SongAdapter {
            songService?.setPlaying(it)
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    androidx.appcompat.R.anim.abc_fade_in,
                    androidx.appcompat.R.anim.abc_fade_out,
                    androidx.appcompat.R.anim.abc_fade_in,
                    androidx.appcompat.R.anim.abc_fade_out
                )
                .replace(
                    R.id.container,
                    DetailFragment.newInstance(it.id),
                    "ToDetail"
                )
                .addToBackStack("BackToList")
                .commit()
        }
        binding.run {
            adapter?.let {
                rvSong.adapter = ScaleInAnimationAdapter(it)
                rvSong.addItemDecoration(itemDecoration)
                it.submitList(songService?.getSongList())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        songService = null
        super.onDestroy()
    }
}