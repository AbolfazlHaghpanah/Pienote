package pienote.data.models

import androidx.room.Embedded
import pienote.data.entity.CategoryEntity
import pienote.features.home.domain.model.CategoryWithNotesCountDomainModel

data class CategoryWithNotesCount(
    @Embedded val category: CategoryEntity,
    val notesCount: Int
) {
    fun toDomainModel() = CategoryWithNotesCountDomainModel(
        id = category.id,
        name = category.name,
        priority = category.priority,
        image = category.image,
        noteCount = notesCount
    )
}
