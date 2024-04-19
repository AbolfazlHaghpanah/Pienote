package com.haghpanh.pienote.feature_category.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.haghpanh.pienote.feature_category.data.relations.CategoryWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun observeCategory(categoryId: Int) : Flow<CategoryWithNotes>
}
