package com.haghpanah.pienote.model

data class NoteWithCategoryDomainModel(
    val note: NoteDomainModel,
    val category: CategoryDomainModel? = null
)
