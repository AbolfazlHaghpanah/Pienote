package com.haghpanah.pienote.utils

import com.haghpanah.pienote.database.Categories
import com.haghpanah.pienote.database.GetCategoryById
import com.haghpanah.pienote.database.GetCategoryWithNoteCount
import com.haghpanah.pienote.database.GetNoteWithCategoryById
import com.haghpanah.pienote.database.Notes
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.model.NoteWithCategoryDomainModel




























internal fun Notes.toCategoryDomainModel() =
    NoteDomainModel(
        id = id,
        title = title,
        markdown = markdown,
        image = image,
        addedTime = added_time,
        lastChangedTime = last_changed_time,
        categoryId = category_id,
        color = color
    )

internal fun noteMapper(
    id: Long,
    title: String,
    markdown: String,
    image: String?,
    addedTime: String,
    lastChangedTime: String?,
    categoryId: Long?,
    color: String?
) = NoteDomainModel(
    id = id,
    title = title,
    markdown = markdown,
    image = image,
    addedTime = addedTime,
    lastChangedTime = lastChangedTime,
    categoryId = categoryId,
    color = color
)

internal fun GetNoteWithCategoryById.toCategoryDomainModel() =
    NoteWithCategoryDomainModel(
        note = NoteDomainModel(
            id = id,
            title = title,
            markdown = markdown,
            image = image,
            addedTime = added_time,
            lastChangedTime = last_changed_time,
            categoryId = category_id,
            color = color
        ),
        category = id_?.let {
            CategoryDomainModel(
                id = it,
                name = name!!,
                priority = priority?.toInt(),
                image = image_
            )
        }
    )

internal fun categoryMapper(
    id: Long,
    name: String,
    priority: Long?,
    image: String?
) = CategoryDomainModel(
    id = id,
    name = name,
    priority = priority?.toInt(),
    image = image
)

internal fun NoteDomainModel.toNotes() = Notes(
    title = title,
    id = id,
    markdown = markdown,
    image = image,
    added_time = addedTime,
    last_changed_time = lastChangedTime,
    category_id = categoryId,
    color = color,
)

internal fun GetCategoryById.toCategoryDomainModel() =
    CategoryDomainModel(
        id = id,
        name = name,
        priority = priority?.toInt(),
        image = image
    )

internal fun GetCategoryById.toNoteDomainModel() = NoteDomainModel(
    id = id_ ?: error("note id was null when mapping"),
    title = title.orEmpty(),
    markdown = markdown.orEmpty(),
    image = image_,
    addedTime = added_time.orEmpty(),
    lastChangedTime = last_changed_time,
    categoryId = category_id,
    color = color
)

internal fun GetCategoryWithNoteCount.toDomainModel() = CategoryWithNotesCountDomainModel(
    id = id,
    name = name,
    priority = priority?.toInt(),
    image = image,
    noteCount = COUNT.toInt()
)

internal fun CategoryDomainModel.toSqlCategories() = Categories(
    id = id,
    name = name,
    priority = priority?.toLong(),
    image = image
)