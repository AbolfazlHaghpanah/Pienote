package com.haghpanh.pienote.note.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.haghpanh.pienote.commondata.entity.BaseEntity
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.note.domain.model.NoteWithCategoryDomainModel

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