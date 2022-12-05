package com.itis.androidcourse.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.itis.androidcourse.MediaActions
import com.itis.androidcourse.R
import com.itis.androidcourse.repo.SongRepository

private const val CHANNEL_ID = "music_channel"

class NotificationService(
    private val context: Context
) {

    private var builder: NotificationCompat.Builder

    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val prevIntent = Intent(context, MediaService::class.java).apply {
        action = "PREV"
    }.let {
        PendingIntent.getService(
            context,
            0,
            it,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val resumeIntent = Intent(context, MediaService::class.java).apply {
        action = "RESUME"
    }.let {
        PendingIntent.getService(
            context,
            1,
            it,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val nextIntent = Intent(context, MediaService::class.java).apply {
        action = "NEXT"
    }.let {
        PendingIntent.getService(
            context,
            2,
            it,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val stopIntent = Intent(context, MediaService::class.java).apply {
        action = "STOP"
    }.let {
        PendingIntent.getService(
            context,
            3,
            it,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_title),
                IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_description)

            }.also {
                manager.createNotificationChannel(it)
            }
        }

        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.default_song)
            .addAction(R.drawable.previous, MediaActions.PREV.name, prevIntent)
            .addAction(R.drawable.pause, MediaActions.PLAY.name, resumeIntent)
            .addAction(R.drawable.next, MediaActions.NEXT.name, nextIntent)
            .addAction(R.drawable.stop, MediaActions.STOP.name, stopIntent)

    }

    fun setNotification(currentSongId: Int): Notification {
        val song = SongRepository.getSongById(currentSongId)
        val icon = BitmapFactory.decodeResource(context.resources, song.cover)

        val songIntent = Intent(context, MediaService::class.java).apply {
            action = "SONG"
        }.let {
            PendingIntent.getService(
                context,
                3,
                it,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        val nBuilder = builder
            .setContentTitle(song.name)
            .setContentText(song.author)
            .setLargeIcon(icon)
            .setContentIntent(songIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setShowWhen(false)
            .setAutoCancel(false)
            .setSilent(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        builder = nBuilder
        val notification = nBuilder.build()
        manager.notify(1, notification)
        return notification
    }

    @SuppressLint("RestrictedApi")
    fun setState(state: String) {
        if (state == "PLAY") {
            builder.mActions[1] =
                NotificationCompat.Action(R.drawable.pause, MediaActions.PLAY.name, resumeIntent)
        } else if (state == "PAUSE") {
            builder.mActions[1] =
                NotificationCompat.Action(R.drawable.play, MediaActions.PLAY.name, resumeIntent)
        }
        manager.notify(1, builder.build())
    }

    fun cancelNotification() {
        manager.cancelAll()
    }
}