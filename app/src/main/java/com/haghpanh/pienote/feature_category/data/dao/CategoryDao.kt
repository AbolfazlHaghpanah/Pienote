package com.haghpanh.pienote.feature_category.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_category.data.relations.CategoryWithNotes
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

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}
