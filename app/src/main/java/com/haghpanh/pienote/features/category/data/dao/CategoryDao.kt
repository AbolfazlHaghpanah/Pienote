package com.haghpanh.pienote.features.category.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.features.category.data.relations.CategoryWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun observeCategory(categoryId: Int): Flow<CategoryWithNotes>

    @Query(
        """
        UPDATE notes
        SET category_id = null 
        WHERE id = :noteId
        """
    )
    suspend fun deleteNoteFromCategory(noteId: Int)

    @Query(
        """
        UPDATE notes
        SET category_id = :categoryId
        WHERE id = :noteId
        """
    )
    suspend fun addNoteToCategory(noteId: Int, categoryId: Int)


    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM NOTES")
    fun observeAvailableNotes() : Flow<List<NoteEntity>>
}
