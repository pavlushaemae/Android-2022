package com.itis.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LayoutInflater.from(applicationContext)
            .inflate(R.layout.activity_main, null, false).also {
                setContentView(it)
            }
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        if (savedInstanceState == null) {
            binding?.run {
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