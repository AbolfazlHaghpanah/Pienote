package com.haghpanh.pienote.note.data.localdateasource

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondata.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun observeNote(id: Int): Flow<NoteWithCategory>
    fun getCategories(): List<CategoryEntity>
    suspend fun insertNote(note : NoteEntity)
    suspend fun updateNote(note: NoteEntity)
}
