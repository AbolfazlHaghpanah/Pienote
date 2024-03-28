package com.haghpanh.pienote.home.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.haghpanh.pienote.commondata.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Query("select * from notes order by id desc")
    fun observeNotes(): Flow<List<NoteEntity>>

    @Insert
    suspend fun insertNotes(note: NoteEntity)
}