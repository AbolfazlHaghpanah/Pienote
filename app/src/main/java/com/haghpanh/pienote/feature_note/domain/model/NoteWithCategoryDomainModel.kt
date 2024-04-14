package com.haghpanh.pienote.feature_note.domain.model

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel

data class NoteWithCategoryDomainModel(
    val note: NoteDomainModel,
    val category : CategoryDomainModel? = null
)
