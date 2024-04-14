package com.haghpanh.pienote.feature_category.data.databasviews

import androidx.room.DatabaseView
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity

@DatabaseView
data class NotesWithCategory(
    val category: CategoryEntity,
    val notes : List<NoteEntity>
)
