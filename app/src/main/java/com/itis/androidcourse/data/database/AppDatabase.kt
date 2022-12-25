package com.itis.androidcourse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.itis.androidcourse.data.dao.NoteDao
import com.itis.androidcourse.data.entity.Note
import com.itis.androidcourse.data.entity.NoteDateConverter

@Database(entities = [Note::class] , version = 3)
@TypeConverters(NoteDateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}
