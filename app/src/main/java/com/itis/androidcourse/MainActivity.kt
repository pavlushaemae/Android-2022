package com.itis.androidcourse

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        if (savedInstanceState == null) {
            binding?.run {
//                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    LayoutInflater.from(applicationContext).inflate(R.layout.activity_main, null, false).also {
//                        setContentView(it)
//                    }
//
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container_first, FirstFragment(), "TAG")
//                        .setCustomAnimations(
//                            android.R.anim.fade_in,
//                            android.R.anim.fade_out,
//                            android.R.anim.fade_in,
//                            android.R.anim.fade_out
//                        )
//                        .commit()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container_second, SecondFragment.newInstance(0), "Tag")
//                        .addToBackStack("ToFirstFragment")
//                        .commit()
//                }
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    LayoutInflater.from(applicationContext).inflate(R.layout.activity_port, null, false).also {
                        setContentView(it)
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FirstFragment(), "TAG")
                        .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        .commit()
                }
            }
        }

    }
}