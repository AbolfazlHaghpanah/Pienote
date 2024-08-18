package com.haghpanh.pienote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.data.models.CategoryWithNotesCount
import com.haghpanh.pienote.data.relation.CategoryWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(
        """
            SELECT categories.*, COUNT(notes.id) as notesCount
            FROM categories     
            LEFT JOIN notes ON category_id = categories.id 
            GROUP BY categories.id 
            ORDER BY id DESC
            """
    )
    fun observeCategoriesWithNotesCount(): Flow<List<CategoryWithNotesCount>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun observeCategoryById(categoryId: Int): Flow<CategoryWithNotes>

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryEntity>

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: CategoryEntity)
}
