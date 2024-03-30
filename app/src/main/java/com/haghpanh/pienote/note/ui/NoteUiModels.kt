package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

@Immutable
data class Note(
    val id: Int,
    val title: String,
    val note: String,
    val image: String? = null,
    val addedTime: String,
    val lastChangedTime: String? = null,
    val categoryId: Int? = null,
    val priority: Int? = null
) {
    fun toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            priority = priority
        )
}

@Immutable
data class Category(
    val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?
)
