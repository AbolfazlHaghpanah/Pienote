package com.haghpanh.pienote.features.home.domain.model

data class CategoryWithNotesCountDomainModel(
    val id: Int,
    val name: String,
    val priority: Int?,
    val image: String?,
    val noteCount: Int
)
