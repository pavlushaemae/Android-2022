package com.itis.androidcourse.provider

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.itis.androidcourse.R
import com.itis.androidcourse.SecondActivity

class NotificationProvider(private val context: Context) {

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(title: String, text: String) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val vibrations = arrayOf(150L, 250L).toLongArray()
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val sound: Uri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.alarm
        )
        val intent = Intent(context, SecondActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pending = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.default_notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.default_notification_channel_id),
                context.getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.BLUE
                vibrationPattern = vibrations
                setSound(sound, audioAttributes)
                setShowBadge(false)
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        } else {
            builder
                .setVibrate(vibrations)
                .setSound(sound)
                .setLights(Color.BLUE, 100, 500)
        }
        notificationManager.notify(1, builder.build())
    }
}