package pienote.data.utils

import pienote.commondomain.model.NoteDomainModel
import pienote.data.entity.NoteEntity

fun NoteDomainModel.toEntity(isNew: Boolean = false): NoteEntity =
    if (isNew) {
        NoteEntity(
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            color = color
        )
    } else {
        NoteEntity(
            id = id,
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            color = color
        )
    }
