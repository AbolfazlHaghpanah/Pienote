package com.haghpanh.pienote.features.category.domain.model

import com.haghpanh.pienote.commondomain.model.NoteDomainModel

data class CategoryWithNotesDomainModel(
    val id : Int,
    val name : String,
    val priority : Int?,
    val image : String?,
    val notes : List<NoteDomainModel>
)
