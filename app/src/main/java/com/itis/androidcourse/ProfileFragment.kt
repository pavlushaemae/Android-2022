package com.itis.androidcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.itis.androidcourse.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = navOptions {

            launchSingleTop = false
            popUpTo(R.id.profileFragment)
            anim {
                enter = android.R.anim.slide_in_left
                exit = android.R.anim.slide_out_right
                popEnter = android.R.anim.slide_in_left
                popExit = android.R.anim.slide_out_right
            }
        }

        binding.run {
            btnEdit.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_editProfileFragment,
                    null,
                    options
                )
            }
            btnFavourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_favouritesFragment,
                    null,
                    options
                )
            }
            btnFriends.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_friendsFragment,
                    null,
                    options
                )
            }
            btnNotes.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_notesFragment,
                    null,
                    options
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}