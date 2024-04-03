package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

@Immutable
data class Note(
    val id: Int = -1,
    val title: String? = null,
    val note: String? = null,
    val image: String? = null,
    val addedTime: String? = null,
    val lastChangedTime: String? = null,
    val categoryId: Int? = null,
    val priority: Int? = null
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
            priority = priority
        )
}

@Immutable
data class Category(
    val id: Int = 0,
    val name: String = "",
    val priority: Int? = null,
    val image: String? = null
)
