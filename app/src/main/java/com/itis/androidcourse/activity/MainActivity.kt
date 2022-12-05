package com.itis.androidcourse.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.itis.androidcourse.R
import com.itis.androidcourse.fragment.DetailFragment
import com.itis.androidcourse.fragment.ListFragment
import com.itis.androidcourse.repo.SongRepository
import com.itis.androidcourse.service.MediaService

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MediaService::class.java)
        ContextCompat.startForegroundService(this, intent)

        val id = getIntent().getIntExtra("arg_id", -1)
        val currentPos = getIntent().getIntExtra("arg_current", -1)
        val duration = getIntent().getIntExtra("arg_duration", -1)

        if (id == -1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment(), "ToListFragment")
                .commit()
        } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance(id, currentPos, duration)).commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SongRepository.stopAllSongs()
    }
}