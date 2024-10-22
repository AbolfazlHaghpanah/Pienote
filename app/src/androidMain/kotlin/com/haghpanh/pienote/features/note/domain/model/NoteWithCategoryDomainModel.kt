package pienote.features.note.domain.model

import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel

data class NoteWithCategoryDomainModel(
    val note: NoteDomainModel,
    val category: CategoryDomainModel? = null
)
