package com.haghpanh.pienote.commondata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.haghpanh.pienote.commondata.entity.NoteEntity

@Dao
interface CommonDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNotes(note: NoteEntity)
}
