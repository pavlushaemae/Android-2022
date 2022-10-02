package com.itis.androidcourse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.itis.androidcourse.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = intent
        val action = intent.action
        val type = intent.type
        if (Intent.ACTION_SEND == action && type != null) {
            when {
                "text/plain" == type -> {
                    pickText(intent)
                }
                type.startsWith("image/") -> {
                    pickImage(intent)
                }
                type.startsWith("video/") -> {
                    pickVideo(intent)
                }
            }
        }
    }

    private fun pickText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        sharedText?.apply {
            with(binding) {
                tvIntent.visibility = View.VISIBLE
                vvIntent.visibility = View.GONE
                ivIntent.visibility = View.GONE
                tvIntent.text = sharedText
            }
        }
    }

    private fun pickImage(intent: Intent) {
        val imageUri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as Uri?
        imageUri?.apply {
            binding.apply {
                ivIntent.visibility = View.VISIBLE
                vvIntent.visibility = View.GONE
                tvIntent.visibility = View.GONE
                ivIntent.setImageURI(imageUri)
            }
        }
    }

    private fun pickVideo(intent: Intent) {
        val videoUri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as Uri?
        videoUri?.apply {
            binding.apply {
                vvIntent.visibility = View.VISIBLE
                ivIntent.visibility = View.GONE
                tvIntent.visibility = View.GONE
                vvIntent.setVideoURI(videoUri)
                val mediaController = MediaController(this@SecondActivity)
                mediaController.setAnchorView(vvIntent)
                mediaController.setMediaPlayer(vvIntent)
                vvIntent.setMediaController(mediaController)
                vvIntent.start()
            }
        }
    }
}