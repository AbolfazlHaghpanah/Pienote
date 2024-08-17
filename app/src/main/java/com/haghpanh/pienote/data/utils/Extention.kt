package com.haghpanh.pienote.data.utils

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.data.entity.NoteEntity

fun NoteDomainModel.toEntity(isNew: Boolean = false): NoteEntity =
    if (isNew) {
        NoteEntity(
            title = title,
            note = note,
            image = image,
            addedTime = addedTime,
            lastChangedTime = lastChangedTime,
            categoryId = categoryId,
            priority = priority
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
            priority = priority
        )
    }
