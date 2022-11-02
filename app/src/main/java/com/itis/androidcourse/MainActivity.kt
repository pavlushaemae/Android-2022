package com.itis.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val controller =
            (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.profileFragment,
                R.id.chatFragment,
                R.id.musicFragment,
                R.id.videoFragment,
                R.id.settingsFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        findViewById<Toolbar>(androidx.appcompat.R.id.action_bar).setupWithNavController(
            controller,
            appBarConfiguration
        )

        binding?.run {
            bnvMain.setupWithNavController(controller)

        }
    }
}