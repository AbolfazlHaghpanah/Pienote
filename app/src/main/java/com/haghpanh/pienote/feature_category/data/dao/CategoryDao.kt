package com.haghpanh.pienote.feature_category.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_category.data.databasviews.NotesWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("""
        select * from notes
        left join categories 
        on categories.id = category_id 
        where category_id = :categoryId
    """)
    fun getCategoryWithNotes(categoryId: Int): Flow<NotesWithCategory>

    @Query("SELECT * FROM notes WHERE category_id = :categoryId")
    fun observeCategoriesNotes(categoryId: Int): Flow<List<NoteEntity>>
}
