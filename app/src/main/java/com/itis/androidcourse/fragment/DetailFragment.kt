package com.itis.androidcourse.fragment

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itis.androidcourse.IMediaAidlInterface
import com.itis.androidcourse.service.MediaService
import com.itis.androidcourse.R
import com.itis.androidcourse.databinding.FragmentDetailBinding
import com.itis.androidcourse.model.Song
import com.itis.androidcourse.repo.SongRepository
import com.itis.androidcourse.service.SongService
import java.util.concurrent.TimeUnit

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var binding: FragmentDetailBinding? = null
    private var songService: SongService? = null
    private var duration: Int? = null
    private var binder: IMediaAidlInterface? = null
    private var handler: Handler? = null
    private var song: Song? = null
    private var isPlaying: Boolean = true

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = IMediaAidlInterface.Stub.asInterface(service)
            initAll()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    private fun initAll() {
        val id: Int = arguments?.run {
            getInt(ARG_ID)
        } ?: 0
        songService = SongService()
        song = songService?.getSongById(id)

        binding?.run {
            song?.let {
                ivCover.setImageResource(it.cover)
                tvName.text = it.name
                tvAuthor.text = it.author
                tvAlbum.text = it.album
            }
        }
        val currentPos: Int = arguments?.run {
            getInt(ARG_CURRENT)
        } ?: 0
        val argDuration: Int = arguments?.run {
            getInt(ARG_DURATION)
        } ?: 0
        if (currentPos != 0) {
            duration = argDuration
            binding?.sbProgress?.progress = currentPos

        } else {
            duration = binder?.setMusic(song)
            binder?.playMusic()
        }
        initActions(song)
    }

    private fun initActions(song: Song?) {
        song?.let {
            initSeekBar()
            runSeekBar()
            binding?.run {
                ivPlay.setOnClickListener {
                    play()
                }
                ivPrevious.setOnClickListener {
                    previous(song)
                }
                ivNext.setOnClickListener {
                    next(song)
                }
                ivPause.setOnClickListener {
                    pause()
                }
                ivClose.setOnClickListener {
                    stop()
                }
            }
        }
    }

    private fun updateFragment(song: Song?) {
        this.song = song

        binding?.run {
            song?.let {
                ivCover.setImageResource(it.cover)
                tvName.text = it.name
                tvAuthor.text = it.author
                tvAlbum.text = it.album
            }
        }
        showPause()
        initActions(song)
    }

    private fun previous(song: Song?) {
        binding?.sbProgress?.progress = 0
        stopSeekBar()
        song?.let {
            binder?.playPrev()
            updateFragment(songService?.prevMusic(song))
        }
        runSeekBar()
    }

    private fun stop() {
        SongRepository.stopAllSongs()
        binder?.stopMusic()
        parentFragmentManager.popBackStack()
    }

    private fun next(song: Song?) {
        binding?.sbProgress?.progress = 0
        stopSeekBar()
        song?.let {
            binder?.playPrev()
            updateFragment(songService?.nextMusic(song))
        }
        runSeekBar()
    }

    private fun pause() {
        binder?.pauseMusic()
        isPlaying = false
        showPlay()
    }

    private fun play() {
        binder?.playMusic()
        runSeekBar()
        isPlaying = true
        showPause()
    }

    private fun showPause() {
        binding?.run {
            ivPlay.visibility = View.GONE
            ivPause.visibility = View.VISIBLE
        }
    }

    private fun showPlay() {
        binding?.run {
            ivPlay.visibility = View.VISIBLE
            ivPause.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.bindService(
            Intent(activity, MediaService::class.java),
            connection,
            AppCompatActivity.BIND_AUTO_CREATE
        )
        binding = FragmentDetailBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopSeekBar()
    }

    private fun stopSeekBar() {
        handler = null
    }

    private fun runSeekBar() {
        duration?.let { duration ->
            binding?.run {
                sbProgress.max = duration
                tvFullTime.text = String.format(
                    "%2d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
                )

            }
        }
        var currentPos: Int
        handler = Handler(Looper.getMainLooper())
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                binder?.let {
                    currentPos = it.currentPosition
                    binding?.sbProgress?.progress = currentPos
                    binding?.tvCurrentTime?.text = String.format(
                        "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(currentPos.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(currentPos.toLong()) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        currentPos.toLong()
                                    )
                                )
                    )
                    handler?.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun initSeekBar() {
        binding?.sbProgress?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binder?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    override fun onDestroy() {
        stopSeekBar()
        binding = null
        songService = null
        song = null
        duration = null
        handler = null
        super.onDestroy()
    }

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_CURRENT = "arg_current"
        private const val ARG_DURATION = "arg_duration"

        fun newInstance(idOfSong: Int, currentPos: Int = 0, argDuration: Int = 0) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, idOfSong)
                    putInt(ARG_CURRENT, currentPos)
                    putInt(ARG_DURATION, argDuration)
                }
            }
    }
}