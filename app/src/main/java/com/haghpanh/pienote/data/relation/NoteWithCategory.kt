package com.haghpanh.pienote.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.data.entity.NoteEntity
import com.haghpanh.pienote.features.note.domain.model.NoteWithCategoryDomainModel

data class NoteWithCategory(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity?
) {
    fun toDomainModel(): NoteWithCategoryDomainModel =
        NoteWithCategoryDomainModel(
            note = note.toDomainModel(),
            category = category?.toDomainModel()
        )
}
