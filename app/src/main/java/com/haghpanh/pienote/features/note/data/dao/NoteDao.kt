package com.haghpanh.pienote.features.note.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.features.note.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("select * from notes where id =:id")
    fun observeNote(id: Int): Flow<NoteWithCategory>

    @Query("select * from categories")
    suspend fun getCategories(): List<CategoryEntity>

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}
