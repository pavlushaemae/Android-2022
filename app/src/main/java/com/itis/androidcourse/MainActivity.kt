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
                if (tvName.visibility == View.GONE) {
                    tvName.visibility = View.VISIBLE
                    ivArrow.visibility = View.VISIBLE
                } else {
                    tvName.visibility = View.GONE
                    ivArrow.visibility = View.GONE
                }
            }
        }
    }
}