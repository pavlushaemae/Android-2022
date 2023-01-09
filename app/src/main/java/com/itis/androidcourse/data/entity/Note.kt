package com.itis.androidcourse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var title: String,
    var description: String,
    val date: Date?,
    val longitude: Double?,
    val latitude: Double?,
)
