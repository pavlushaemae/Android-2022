package com.itis.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidcourse.ui.fragments.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment(), "toList").commit()
    }
}