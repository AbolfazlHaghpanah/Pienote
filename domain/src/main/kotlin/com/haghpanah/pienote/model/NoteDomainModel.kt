package com.haghpanah.pienote.model

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

fun emptyNote() = NoteDomainModel(
    id = -1,
    title = "",
    markdown = "",
    image = null,
    addedTime = "",
    lastChangedTime = null,
    categoryId = null,
    color = null
)
