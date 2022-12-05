package com.itis.androidcourse.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Int,
    val name: String,
    val author: String,
    val album: String,
    @DrawableRes val cover: Int,
    @RawRes val raw: Int,
    var isPlaying: Boolean = false
) : Parcelable
