package com.haghpanh.pienote.note.domain.model

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

data class NoteWithCategoryDomainModel(
    val note : NoteDomainModel,
    val category : CategoryDomainModel
)
