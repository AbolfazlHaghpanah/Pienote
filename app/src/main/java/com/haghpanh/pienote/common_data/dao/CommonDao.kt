package com.haghpanh.pienote.common_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.haghpanh.pienote.common_data.entity.NoteEntity

@Dao
interface CommonDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNotes(note: NoteEntity)
}