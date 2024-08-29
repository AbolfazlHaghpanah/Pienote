package com.haghpanh.pienote.features.home.ui

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
    val color: String? = null
) {
    fun toDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = id,
            title = title.orEmpty(),
            note = note.orEmpty(),
            image = image,
            addedTime = addedTime.orEmpty(),
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            color = color
        )
}

@Immutable
data class Category(
    val id: Int = 0,
    val name: String,
    val priority: Int?,
    val image: String?,
    val noteCounts: Int
)
