package com.itis.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnEdit.setOnClickListener {
                if (groupName.visibility == View.GONE) {
                    groupName.visibility = View.VISIBLE
                } else {
                    groupName.visibility = View.GONE
                }
            }
        }
    }
}