package com.itis.androidcourse.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.itis.androidcourse.data.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun save(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE note.id = :id")
    suspend fun getNoteById(id: Int): Note?
}