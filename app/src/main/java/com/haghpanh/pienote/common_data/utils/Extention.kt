package com.haghpanh.pienote.common_data.utils

import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel

fun CategoryDomainModel.toEntity(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
        priority = priority,
        image = image
    )

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
