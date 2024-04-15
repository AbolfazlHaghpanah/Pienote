package com.haghpanh.pienote.feature_category.data.databasviews

import androidx.room.DatabaseView
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity

@DatabaseView(
    """
       select * from (
            select 
            notes.id as noteId,
            title as noteTitle,
            note as content,
            categories.id as categoryId,
            categories.name as categoryName,
            categories.image as categoryImage,
            categories.priority as categoryPriority
            from notes left join categories on notes.category_id = categories.id 
        ) where categoryId not null
        """
)
data class NotesWithCategoryView(
    val noteId: Int,
    val noteTitle: String,
    val content: String,
    val categoryId: Int,
    val categoryName: String,
    val categoryImage: String?,
    val categoryPriority: Int?
)

data class NoteWithCategory(
    val category: CategoryEntity,
    val notes : List<NoteEntity>
)