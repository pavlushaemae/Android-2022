package com.itis.androidcourse

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            btn1.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "message")
                intent.type = "text/plan"

                val chooserIntent = Intent.createChooser(
                    intent,
                    "Сообщение"
                )
                if (chooserIntent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

            btn2.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_CALL
                intent.putExtra(Intent.EXTRA_TEXT, Uri.parse("+79911153704"))
                intent.type = "text/plan"

                val chooserIntent = Intent.createChooser(
                    intent,
                    "Звонок"
                )
                if (chooserIntent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

            btn3.setOnClickListener {
//                val intent = Intent(Intent.ACTION_CREATE_NOTE).apply {
//                    putExtra(NoteIntents.EXTRA_NAME, subject)
//                    putExtra(NoteIntents.EXTRA_TEXT, text)
//                }
//                if (intent.resolveActivity(packageManager) != null) {
//                    startActivity(intent)
//                }

            }
        }

    }
}