package com.haghpanh.pienote.commondata.utils

import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel

fun CategoryDomainModel.toEntity(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
        priority = priority,
        image = image
    )

fun NoteDomainModel.toEntity() :NoteEntity =
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