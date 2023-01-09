package com.itis.androidcourse.data.entity

import androidx.room.TypeConverter
import java.util.*

class NoteDateConverter {
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }
}