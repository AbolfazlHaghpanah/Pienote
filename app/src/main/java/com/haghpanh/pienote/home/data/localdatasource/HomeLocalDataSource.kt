package com.haghpanh.pienote.home.data.localdatasource

import com.haghpanh.pienote.commondata.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeNotes(): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
}