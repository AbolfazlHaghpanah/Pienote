package com.haghpanh.pienote.note.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.note.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("select * from notes where id =:id")
    fun observeNote(id: Int): Flow<NoteWithCategory>

    @Query("select * from categories")
    fun getCategories(): List<CategoryEntity>

    @Insert
    suspend fun insertNote(note : NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}