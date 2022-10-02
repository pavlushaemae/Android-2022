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
                if (etMessage.text.toString() != "") {
                    sendMessage(etMessage.text.toString())
                }
            }

            btn2.setOnClickListener {
                if (etNumber.text.toString() != "") {
                    callToNum(etNumber.text.toString())
                }
            }

            btn3.setOnClickListener {
                if (etUrl.text.toString() != "") {
                    openWebPage(etUrl.text.toString())
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "text/plan"

        val chooserIntent = Intent.createChooser(
            intent,
            "Сообщение"
        )
        if (chooserIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        }
    }

    private fun callToNum(num: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$${num}")
        }
        val chooserIntent = Intent.createChooser(
            intent,
            "Звонок"
        )
        if (chooserIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        }
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        val chooserIntent = Intent.createChooser(
            intent,
            "Страница в вк"
        )
        if (chooserIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        }
    }
}