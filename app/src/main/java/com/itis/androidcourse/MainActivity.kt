package com.itis.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidcourse.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FirstFragment(), "ToFirst")
            .commit()
    }
}