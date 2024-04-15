package com.haghpanh.pienote.feature_category.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_category.data.databasviews.NotesWithCategoryView
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("""
        select 
        notes.id as noteId,
        notes.title as noteTitle,
        notes.note as content,
        categories.id as categoryId,
        categories.name as categoryName,
        categories.image as categoryImage,
        categories.priority as categoryPriority
        from notes left join categories on notes.category_id = categories.id 
        where categoryId = :categoryId
    """)
    fun getCategoryWithNotes(categoryId: Int): Flow<List<NotesWithCategoryView>>



    @Query("SELECT * FROM notes WHERE category_id = :categoryId")
    fun observeCategoriesNotes(categoryId: Int): Flow<List<NoteEntity>>
}
