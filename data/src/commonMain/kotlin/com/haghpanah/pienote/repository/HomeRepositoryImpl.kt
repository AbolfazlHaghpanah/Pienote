package com.haghpanah.pienote.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.haghpanah.pienote.database.Notes
import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.utils.noteMapper
import com.haghpanah.pienote.utils.toCategoryDomainModel
import com.haghpanah.pienote.utils.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class HomeRepositoryImpl(
    private val database: PienoteDatabase,
) : HomeRepository {
    override fun observeNotes(): Flow<List<NoteDomainModel>> =
        database.noteQueries
            .getNotes(mapper = ::noteMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>> =
        database.categoriesQueries
            .getCategoryWithNoteCount()
            .asFlow()
            .mapToList(Dispatchers.IO).map { flow ->
                flow.map { it.toDomainModel() }
            }

    override fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>> =
        database.noteQueries
            .getNotesByCategoryId(categoryId.toLong())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { flow ->
                flow.map { notes: Notes ->
                    notes.toCategoryDomainModel()
                }
            }

    override suspend fun deleteNote(note: NoteDomainModel) {
        //TODO Perform List remove
        database.noteQueries.deleteNote(listOf(note.id))
    }
}
