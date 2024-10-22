package pienote.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import pienote.data.entity.CategoryEntity
import pienote.data.entity.NoteEntity
import pienote.features.note.domain.model.NoteWithCategoryDomainModel

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
