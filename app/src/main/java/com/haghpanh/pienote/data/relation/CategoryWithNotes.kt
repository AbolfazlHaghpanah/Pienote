package com.haghpanh.pienote.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.data.entity.NoteEntity

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
