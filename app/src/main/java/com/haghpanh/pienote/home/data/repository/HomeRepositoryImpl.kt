package com.haghpanh.pienote.home.data.repository

import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.home.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import com.haghpanh.pienote.commondata.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {
    override fun observeNotes(): Flow<List<NoteDomainModel>> {
        val notesFlow = homeLocalDataSource.observeNotes()

        return notesFlow.map { notes ->
            notes.map { note ->
                note.toDomainModel()
            }
        }
    }

    override fun observeCategories(): Flow<List<CategoryDomainModel>> {
        val categoriesFlow = homeLocalDataSource.observeCategories()

        return categoriesFlow.map { categories ->
            categories.map { category ->
                category.toDomainModel()
            }
        }
    }

    override fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>> {
        val notesFlow = homeLocalDataSource.observeNoteByCategory(categoryId)

        return notesFlow.map { notes ->
            notes.map { note ->
                note.toDomainModel()
            }
        }
    }

    override suspend fun insertNote(note: QuickNoteDomainModel) {
        val mappedNote = note.toNoteEntity()

        homeLocalDataSource.insertNote(mappedNote)
    }

    override suspend fun insertCategory(category: CategoryDomainModel) {
        val mappedCategory = category.toEntity()

        homeLocalDataSource.insertCategory(mappedCategory)
    }

    override suspend fun deleteNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity()

        homeLocalDataSource.deleteNote(mappedNote)
    }


    private fun QuickNoteDomainModel.toNoteEntity(): NoteEntity =
        NoteEntity(
            title = title.orEmpty(),
            note = note.orEmpty(),
            image = null,
            addedTime = addedTime,
            lastChangedTime = null,
            categoryId = null,
            priority = null
        )
}