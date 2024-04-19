package com.haghpanh.pienote.feature_category.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity

data class CategoryWithNotes(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        entity = NoteEntity::class,
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val notes : List<NoteEntity>
)