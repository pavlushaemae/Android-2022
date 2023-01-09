package com.itis.androidcourse.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.itis.androidcourse.data.database.AppDatabase
import com.itis.androidcourse.data.entity.Note

class NoteRepository(context: Context) {
    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE note ADD COLUMN date INTEGER")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE note_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " title TEXT NOT NULL,description TEXT NOT NULL, date INTEGER,"
                        + " longitude REAL, latitude REAL)"
            )
            database.execSQL("DROP TABLE note")
            database.execSQL("ALTER TABLE note_new RENAME TO note");
        }
    }

    private val noteDao by lazy {
        db.getNoteDao()
    }

    suspend fun saveNote(note: Note) = noteDao.save(note)


    suspend fun updateNote(note: Note) = noteDao.update(note)


    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    suspend fun deleteAllNotes() = noteDao.deleteAll()

    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)

    suspend fun getNotes(): List<Note> = noteDao.getAll()

    companion object {
        private const val DATABASE_NAME = "itis.android.homework.ten"
    }
}