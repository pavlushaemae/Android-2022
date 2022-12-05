package com.itis.androidcourse.service

import com.itis.androidcourse.model.Song
import com.itis.androidcourse.repo.SongRepository

class SongService() {
    fun getSongList(): List<Song> = SongRepository.songsList
    fun getSongById(id: Int) = SongRepository.getSongById(id)
    fun stopAllSongs() = SongRepository.stopAllSongs()
    fun setPlaying(song: Song) = SongRepository.setPlaying(song)
    fun nextMusic(song: Song): Song = SongRepository.nextMusic(song)
    fun prevMusic(song: Song): Song = SongRepository.prevMusic(song)
}