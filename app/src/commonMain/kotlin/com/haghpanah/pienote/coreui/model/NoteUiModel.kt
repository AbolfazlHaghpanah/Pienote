package com.haghpanah.pienote.coreui.model

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.coredomain.model.NoteDomainModel

@Immutable
data class NoteUiModel(
    val id: Int,
    val title: String,
    val markdown: String,
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
            markdown = markdown.orEmpty(),
            image = image,
            addedTime = addedTime.orEmpty(),
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            color = color
        )
}
