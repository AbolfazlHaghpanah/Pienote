package com.haghpanah.pienote.repository

import com.haghpanah.pienote.database.Categories
import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.utils.toNotes

internal class CommonRepositoryImpl(
    private val database: PienoteDatabase
) : CommonRepository {
    override suspend fun insertNote(note: NoteDomainModel) {
        database.noteQueries.addNote(note.toNotes())
    }

    override suspend fun insertCategory(name: String, image: String?) {
        database.categoriesQueries.addCategory(
            //id and priority will ignored by SQLite
            categories = Categories(
                id = 0,
                name = name,
                priority = null,
                image = image
            )
        )
    }

    override suspend fun addNotesToCategory(noteIds: List<Long>, categoryId: Long) {
        database.noteQueries.addNotesToCategory(
            categoryId = categoryId,
            noteIds = noteIds
        )
    }
}