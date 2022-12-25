package com.itis.androidcourse.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itis.androidcourse.R
import com.itis.androidcourse.data.entity.Note
import com.itis.androidcourse.data.repository.NoteRepository
import com.itis.androidcourse.databinding.FragmentListBinding
import com.itis.androidcourse.recycler.adapter.NoteAdapter
import com.itis.androidcourse.recycler.adapter.SpaceItemDecorator
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.launch


class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var adapter: NoteAdapter? = null
    private var noteRepository: NoteRepository? = null
    private var isLinear: Boolean? = null
    private var itemDecoration: RecyclerView.ItemDecoration? = null

    private var notes: List<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        isLinear = activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(ARG_IS_LINEAR, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        isLinear = true
        when (isLinear) {
            null -> {
                binding.rvNote.layoutManager = LinearLayoutManager(activity)
                isLinear = true
                println("flag1")
            }
            true -> {
                binding.rvNote.layoutManager = LinearLayoutManager(activity)
                println("flag2")
            }
            false -> {
                binding.rvNote.layoutManager = LinearLayoutManager(activity)
                println("flag3")
            }
        }
        context?.also {
            noteRepository = NoteRepository(it)
        }
        itemDecoration =
            SpaceItemDecorator(
                this.requireContext(),
                16.0f
            )

        adapter = isLinear?.let {
            getAdapter(it)
        }
        binding.run {
            adapter?.let {
                rvNote.adapter = ScaleInAnimationAdapter(it)

            }
            itemDecoration?.let {
                rvNote.addItemDecoration(it)
            }
            fabNew.setOnClickListener {
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
                        EditFragment.newInstance(null),
                        "newNote"
                    )
                    .addToBackStack("BackToList")
                    .commit()
            }
        }
        updateNotes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isLinear?.let {

            val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (pref.edit()) {
                putBoolean(ARG_IS_LINEAR, it)
                commit()
            }
        }
    }

    private fun deleteAll() {
        lifecycleScope.launch {
            noteRepository?.deleteAllNotes()
            updateNotes()
        }
    }

    private fun getAdapter(isLinear: Boolean): NoteAdapter {
        return NoteAdapter(isLinear,  {
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
                    EditFragment.newInstance(it.id),
                    "ToEdit"
                )
                .addToBackStack("BackToList")
                .commit()
        }, {
            lifecycleScope.launch {
                noteRepository?.deleteNote(note = it)
                updateNotes()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_delete_all -> {
            deleteAll()
            true
        }

        R.id.action_switch_theme -> {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            true
        }
        R.id.action_switch_layout_manager -> {
            isLinear?.let {
                isLinear = !it
            }
            isLinear?.let {
                adapter = getAdapter(it)
                binding.rvNote.adapter = adapter
                binding.rvNote.layoutManager = if (isLinear == true) {
                    item.icon = resources.getDrawable(R.drawable.view_linear_24, context?.theme)
                    LinearLayoutManager(activity)
                } else {
                    item.icon = resources.getDrawable(R.drawable.grid_view_24, context?.theme)
                    GridLayoutManager(activity, 2)
                }

                println("$it aaaaaaaaaaaaa")
                updateNotes()
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateNotes() {
        lifecycleScope.launch {
            notes = noteRepository?.getNotes()
            println(" notes  ${noteRepository?.getNotes()}")
            binding.apply {
                if (notes.isNullOrEmpty()) {
                    rvNote.visibility = View.GONE
                    tvNoNotes.visibility = View.VISIBLE
                } else {
                    rvNote.visibility = View.VISIBLE
                    tvNoNotes.visibility = View.GONE
                    adapter?.submitList(notes)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        updateNotes()
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        isLinear = null
        noteRepository = null
        super.onDestroy()
    }

    companion object {
        private const val ARG_IS_LINEAR = "ARG_IS_LINEAR"
    }
}