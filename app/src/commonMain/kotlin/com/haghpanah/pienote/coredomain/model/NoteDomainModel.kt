package com.haghpanah.pienote.coredomain.model

data class NoteDomainModel(
    val id: Int,
    val title: String,
    val markdown: String,
    val image: String? = null,
    val addedTime: String,
    val lastChangedTime: String? = null,
    val categoryId: Int? = null,
    val color: String? = null
)
