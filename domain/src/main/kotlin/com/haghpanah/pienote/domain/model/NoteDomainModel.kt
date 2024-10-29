package com.haghpanah.pienote.domain.model

data class NoteDomainModel(
    val id: Long,
    val title: String,
    val markdown: String,
    val image: String? = null,
    val addedTime: String,
    val lastChangedTime: String? = null,
    val categoryId: Long? = null,
    val color: String? = null
)
