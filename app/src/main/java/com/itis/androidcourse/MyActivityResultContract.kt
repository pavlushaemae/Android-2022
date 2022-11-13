package com.itis.androidcourse

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class MyActivityResultContract : ActivityResultContract<Uri?, Bitmap?>() {

    private var context: Context? = null

    override fun createIntent(context: Context, input: Uri?): Intent {
        this.context = context
        val galIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val chooserIntent = Intent.createChooser(galIntent, context.getString(R.string.choose))
        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(camIntent))
        return chooserIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        intent.takeIf { resultCode == Activity.RESULT_OK }?.data?.run {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context?.contentResolver?.let {
                    val source = ImageDecoder.createSource(it, this)
                    ImageDecoder.decodeBitmap(source)
                }
            } else {
                MediaStore.Images.Media.getBitmap(
                    context?.contentResolver,
                    this
                )
            }
            return bitmap
        }
        return intent.takeIf { resultCode == Activity.RESULT_OK }?.getParcelableExtra("data")
    }
}