package com.itis.androidcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.itis.androidcourse.databinding.FragmentProfileContainerBinding

class ProfileContainerFragment: Fragment(R.layout.fragment_profile_container) {

    private var _binding: FragmentProfileContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileContainerBinding.inflate(inflater, container, false)
//        val nestedController =
//            (parentFragmentManager.findFragmentById(R.id.nested_fragment) as NavHostFragment).navController
//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf(
//                R.id.profileFragment,
//            ),
//            fallbackOnNavigateUpListener = requireActivity()::onNavigateUp
//        )
//        requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(androidx.appcompat.R.id.action_bar).setupWithNavController(nestedController,appBarConfiguration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}