package com.haghpanah.pienote.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.haghpanah.pienote.database.Notes
import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.domain.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.domain.model.NoteDomainModel
import com.haghpanah.pienote.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val database: PienoteDatabase
) : HomeRepository {
    init {
        val shouldAddMocks = database.noteQueries.isTableEmpty().executeAsOne()

        if (shouldAddMocks){
            database
            database.noteQueries.addMockNotes()
        }
    }

    override fun observeNotes(): Flow<List<NoteDomainModel>> =
        database.noteQueries
            .getNotes(mapper = ::noteMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>> {
        return flowOf()
    }

    override fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>> =
        database.noteQueries
            .getNotesByCategoryId(categoryId.toLong())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { flow ->
                flow.map { notes: Notes ->
                    notes.toDomainModel()
                }
            }

    override suspend fun deleteNote(note: NoteDomainModel) {
        TODO("Not yet implemented")
    }

    private fun Notes.toDomainModel() =
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

    private fun noteMapper(
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
}