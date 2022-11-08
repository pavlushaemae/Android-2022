package com.itis.androidcourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.androidcourse.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private var binding: ActivitySecondBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}