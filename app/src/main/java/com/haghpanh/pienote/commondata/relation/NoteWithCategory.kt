package com.haghpanh.pienote.commondata.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity

data class NoteWithCategory(
    @Embedded val note : NoteEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category : CategoryEntity
)