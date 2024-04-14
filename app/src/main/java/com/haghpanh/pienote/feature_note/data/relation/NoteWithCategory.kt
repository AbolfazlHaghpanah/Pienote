package com.haghpanh.pienote.feature_note.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.common_data.entity.BaseEntity
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_note.domain.model.NoteWithCategoryDomainModel

data class NoteWithCategory(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity?
) : BaseEntity<NoteWithCategoryDomainModel> {

    override fun toDomainModel(): NoteWithCategoryDomainModel =
        NoteWithCategoryDomainModel(
            note = note.toDomainModel(),
            category = category?.toDomainModel()
        )
}