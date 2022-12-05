package com.itis.androidcourse.repo

import com.itis.androidcourse.R
import com.itis.androidcourse.model.Song

object SongRepository {
    val songsList: List<Song> = arrayListOf(
        Song(
            0,
            "Поломка",
            "SALUKI",
            "Властелин калек",
            R.drawable.polomka,
            R.raw.saluki_polomka
        ),
        Song(
            1,
            "Фиеста",
            "Aarne, Yanix, SEEMEE",
            "AA LANGUAGE",
            R.drawable.aa_language,
            R.raw.aarne_fiesta
        ),
        Song(
            2,
            "99 Problems",
            "Big Baby Tape, kizaru",
            "BANDANA I",
            R.drawable.bandana,
            R.raw.big_baby_tape_kizaru_99_problems
        ),
        Song(
            3,
            "ДВИНУЛСЯ НА ТЕБЕ",
            "GRILLYAZH, Кишлак",
            "ДВИНУЛСЯ НА ТЕБЕ",
            R.drawable.grillyazh,
            R.raw.grillyazh
        ),
        Song(
            4,
            "У России три пути",
            "GSPD, DEAD BLONDE",
            "У России три пути",
            R.drawable.u_rossii_tri_puti,
            R.raw.gspd_u_rossii_tri_puti
        ),
        Song(
            5,
            "Район",
            "Lida", "Район",
            R.drawable.lida_rajjon,
            R.raw.lida_rajjon
        ),
        Song(
            6,
            "Муза",
            "УННВ",
            "Неизданное",
            R.drawable.unnv_neizdannoe,
            R.raw.unnv_muza
        ),
        Song(
            7,
            "АЛЬФА",
            "Жулик",
            "АЛЬФА",
            R.drawable.alfa,
            R.raw.zhulik_alfa
        ),
    )

    fun getSongById(id: Int): Song {
        songsList.forEach {
            if (it.id == id) {
                return it
            }
        }
        return songsList[0]
    }

    fun stopAllSongs() {
        songsList.forEach {
            it.isPlaying = false
        }
    }

    fun setPlaying(song: Song) {
        stopAllSongs()
        song.isPlaying = true
    }

    fun nextMusic(song: Song): Song {
        stopAllSongs()
        songsList.forEach {
            if (it.id == song.id + 1) {
                it.isPlaying = true
                return it
            }
        }
        songsList.first().isPlaying = true
        return songsList.first()
    }

    fun prevMusic(song: Song): Song {
        stopAllSongs()
        songsList.forEach {
            if (it.id == song.id - 1) {
                it.isPlaying = true
                return it
            }
        }
        songsList.last().isPlaying = true
        return songsList.last()
    }
}