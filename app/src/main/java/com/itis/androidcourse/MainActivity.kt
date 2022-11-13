package com.itis.androidcourse

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val settings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val launcher = registerForActivityResult(MyActivityResultContract()) { result ->
        binding?.ivImage?.setImageBitmap(result)
    }
    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            granted?.run {
                when {
                    granted.values.all { true } -> {
                        launcher.launch(null)
                    }
                    !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                            || !shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        showPermsOnSetting()
                    }
                    else -> {
                        binding?.run {
                            Snackbar.make(
                                binding?.root?.rootView!!,
                                getString(R.string.not_allowed),
                                Snackbar.LENGTH_LONG
                            )
                                .setAction(getString(R.string.allow)) { requestPerms() }
                                .show()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.run {
            btnAddImage.setOnClickListener {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA
                    ) ||
                    shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    requestPerms()
                } else {
                    permission.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    )
                }
            }
        }
    }

    private fun requestPerms() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        this.requestPermissions(permissions, 0)
    }

    private fun showPermsOnSetting() {
        binding?.run {
            Snackbar.make(
                root.rootView,
                getString(R.string.need_allow),
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    getString(R.string.settings)
                ) {
                    openApplicationSettings()
                }
                .show()
        }
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + this.packageName)
        )
        settings.launch(appSettingsIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}