package com.haghpanah.pienote.model

data class CategoryWithNotesCountDomainModel(
    val id: Long,
    val name: String,
    val priority: Int?,
    val image: String?,
    val noteCount: Int
)
