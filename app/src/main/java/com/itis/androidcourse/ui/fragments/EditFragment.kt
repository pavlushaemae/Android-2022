package com.itis.androidcourse.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.itis.androidcourse.R
import com.itis.androidcourse.data.entity.Note
import com.itis.androidcourse.data.repository.NoteRepository
import com.itis.androidcourse.databinding.FragmentEditBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment(R.layout.fragment_edit) {

    private var binding: FragmentEditBinding? = null
    private var noteRepository: NoteRepository? = null
    private var note: Note? = null
    private var currentId: Int? = null
    private var locationManager: LocationManager? = null
    private var currentLocation: Location? = null
    private var geocoder: Geocoder? = null
    private var address: Address? = null


    private val settings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}


    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            granted?.run {
                when {
                    granted.values.all { true } -> {
                        addNote()
                    }
                    !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                            || !shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) -> {
                        showPermsOnSetting()
                    }
                    else -> {
                        binding?.run {
                            Snackbar.make(
                                binding?.root?.rootView!!,
                                "Дайте пермишены",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Разрешаю") { requestPerms() }
                                .show()
                        }
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        geocoder = Geocoder(requireContext(), Locale.getDefault())
        locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditBinding.bind(view)
        context?.also {
            noteRepository = NoteRepository(it)
        }
        isExists()
        binding?.run {
            btnSave.setOnClickListener {
                if (currentId == null && isNoteCorrect()) {
                    p()
                } else {
                    currentId?.let {
                        updateNote(it)
                    }
                }
            }
        }
    }

    private fun isExists() {
        lifecycleScope.launch {
            arguments?.getInt(ARG_ID)?.also {
                currentId = it
                note = noteRepository?.getNoteById(it)
                binding?.apply {
                    note?.let { n ->
                        etTitle.setText(n.title)
                        etDescription.setText(n.description)
                        if (n.latitude != null && n.longitude != null) {
                            address =
                                geocoder?.getFromLocation(n.latitude, n.longitude, 1)?.get(0)
                        }
                        tvCoordinates.text = "${address?.getAddressLine(0)}"
                        n.date?.run {
                            tvDate.text =
                                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(
                                    this
                                )
                        }
                    }
                }
            }
        }
    }

    private fun isNoteCorrect(): Boolean {
        binding?.run {
            if (etTitle.text.toString().isEmpty() || etDescription.text.toString().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun updateNote(id: Int) {
        lifecycleScope.launch {
            note = noteRepository?.getNoteById(id)
            binding?.apply {
                if (isNoteCorrect()) {
                    note?.let {
                        it.description = etDescription.text.toString()
                        it.title = etTitle.text.toString()
                        noteRepository?.updateNote(it)
                    }
                }
            }
        }
        parentFragmentManager.popBackStack()
    }

    private fun p() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                addNote()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                requestPerms()
            }
            else -> {
                permission.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addNote() {

        val locationListenerGPS = LocationListener { loc -> currentLocation = loc }
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L,
            500.0f,
            locationListenerGPS
        )
        locationManager?.let {
            currentLocation = it.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }

        lifecycleScope.launch {
            binding?.apply {
                println("currentLoc: $currentLocation")
                currentLocation?.let {
                    noteRepository?.saveNote(
                        Note(
                            title = etTitle.text.toString(),
                            description = etDescription.text.toString(),
                            latitude = it.latitude,
                            longitude = it.longitude,
                            date = Date(),
                            id = null
                        )
                    )
                }
                if (currentLocation == null) {
                    noteRepository?.saveNote(
                        Note(
                            title = etTitle.text.toString(),
                            description = etDescription.text.toString(),
                            latitude = null,
                            longitude = null,
                            date = Date(),
                            id = null
                        )
                    )
                }
                println(note)
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun showPermsOnSetting() {
        binding?.run {
            Snackbar.make(
                root.rootView,
                "Необходимо дать пермишены",
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    "Перейти в настройки"
                ) {
                    openApplicationSettings()
                }
                .show()
        }
    }

    private fun requestPerms() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        activity?.requestPermissions(permissions, 0)
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + requireContext().packageName)
        )
        settings.launch(appSettingsIntent)
    }

    override fun onDestroy() {
        binding = null
        noteRepository = null
        geocoder = null
        currentLocation = null
        currentId = null
        address = null
        note = null
        locationManager = null
        super.onDestroy()
    }

    companion object {

        private const val ARG_ID = "arg_id"

        fun newInstance(idOfNote: Int?) =
            EditFragment().apply {
                println(idOfNote)
                var bundle: Bundle? = null
                idOfNote?.let {
                    bundle = Bundle().apply {
                        putInt(ARG_ID, it)
                    }
                }
                arguments = bundle
            }
    }
}