package com.itis.androidcourse.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import com.itis.androidcourse.IMediaAidlInterface
import com.itis.androidcourse.activity.MainActivity
import com.itis.androidcourse.model.Song
import com.itis.androidcourse.repo.SongRepository

class MediaService : Service() {
    private lateinit var notificationService: NotificationService

    private var mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }
    private var song: Song? = null

    override fun onCreate() {
        super.onCreate()
        notificationService = NotificationService(this)
    }

    private val aidlBinder = object : IMediaAidlInterface.Stub() {
        override fun playMusic() {
            play()
        }

        override fun pauseMusic() {
            pause()
        }

        override fun setMusic(song: Song?): Int {
            return set(song)
        }

        override fun setMusicFromBundle(bundle: Bundle?) {

        }

        override fun getCurrentPosition(): Int {
            return getCurrentPos()
        }

        override fun seekTo(pos: Int) {
            seek(pos)
        }

        override fun playPrev() {
            prev()
        }

        override fun playNext() {
            next()
        }

        override fun stopMusic() {
            stop()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "RESUME" -> if (mediaPlayer.isPlaying) pause() else play()
            "STOP" -> stop()
            "PREV" -> prev()
            "NEXT" -> next()
            "SONG" -> toMain()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = aidlBinder

    private fun toMain() {
        song?.let {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("arg_id", it.id)
                putExtra("arg_current", mediaPlayer.currentPosition)
                putExtra("arg_duration", mediaPlayer.duration)
            }.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

    private fun pause() {
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
        notificationService.setState("PAUSE")
    }

    private fun stop() {
        mediaPlayer.stop()
        notificationService.cancelNotification()
        stopForeground(true)
    }

    private fun play() {
        mediaPlayer.run {
            start()
            notificationService.setState("PLAY")
        }
    }

    private fun next() {
        song?.let {
            song = SongRepository.nextMusic(it)
        }
        set(song)
        play()
    }

    private fun prev() {
        song?.let {
            song = SongRepository.prevMusic(it)
        }
        set(song)
        play()
    }

    private fun set(song: Song?): Int {
        song?.also {
            this.song = song
            startForeground(1, notificationService.setNotification(song.id))
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
            mediaPlayer =
                MediaPlayer.create(applicationContext, it.raw)
            mediaPlayer.run {
                setOnCompletionListener {
                    stop()
                }
            }
        }
        return mediaPlayer.duration
    }

    fun getCurrentPos(): Int {
        return mediaPlayer.currentPosition
    }

    fun seek(pos: Int) {
        mediaPlayer.seekTo(pos)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        song = null
    }
}