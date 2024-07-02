package com.haghpanh.pienote.features.category.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity

data class CategoryWithNotes(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        entity = NoteEntity::class,
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val notes: List<NoteEntity>
)
