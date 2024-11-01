package com.haghpanah.pienote.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.model.NoteWithCategoryDomainModel
import com.haghpanah.pienote.utils.categoryMapper
import com.haghpanah.pienote.utils.toCategoryDomainModel
import com.haghpanah.pienote.utils.toNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NoteRepositoryImpl(
    private val database: PienoteDatabase
) : NoteRepository {
    override fun observeNote(id: Long): Flow<NoteWithCategoryDomainModel> =
        database.noteQueries
            .getNoteWithCategoryById(id)
            .asFlow()
            .mapToOneNotNull(Dispatchers.IO)
            .map { noteWithCategory ->
                noteWithCategory.toCategoryDomainModel()
            }

    override suspend fun getCategories(): List<CategoryDomainModel> =
        database.categoriesQueries
            .getCategories(mapper = ::categoryMapper)
            .executeAsList()


    override suspend fun insertNote(note: NoteDomainModel): Long {
        database.noteQueries.addNote(
            note.toNotes()
        )
        return database.noteQueries.lastInsertId().executeAsOne()
    }

    override suspend fun updateNote(note: NoteDomainModel) {
        database.noteQueries.addOrReplaceNote(
            note.toNotes()
        )
    }
}