//package com.itis.androidcourse.util
//
//import android.content.Context
//import android.location.Location
//import androidx.activity.result.contract.ActivityResultContract
//
//class MyActivityResultContract: ActivityResultContract<Unit?, Location?>() {
//
//    private var context: Context? = null
//
//    override fun createIntent(context: Context, input: Unit?): Intent {
//        this.context = context
//        val galIntent = Intent(Intent.ACTION_PICK).apply {
//            type = "image/*"
//        }
//        val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val chooserIntent = Intent.createChooser(galIntent, context.getString(R.string.choose))
//        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(camIntent))
//        return chooserIntent
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
//        intent.takeIf { resultCode == Activity.RESULT_OK }?.data?.run {
//            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                context?.contentResolver?.let {
//                    val source = ImageDecoder.createSource(it, this)
//                    ImageDecoder.decodeBitmap(source)
//                }
//            } else {
//                MediaStore.Images.Media.getBitmap(
//                    context?.contentResolver,
//                    this
//                )
//            }
//            return bitmap
//        }
//        return intent.takeIf { resultCode == Activity.RESULT_OK }?.getParcelableExtra("data")
//    }
//}