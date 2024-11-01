package com.haghpanah.pienote.model

data class CategoryWithNotesDomainModel(
    val id: Long,
    val name: String,
    val priority: Int?,
    val image: String?,
    val notes: List<NoteDomainModel>
)