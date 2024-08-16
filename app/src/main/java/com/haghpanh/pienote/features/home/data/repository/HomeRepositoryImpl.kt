package com.haghpanh.pienote.features.home.data.repository

import com.haghpanh.pienote.commondata.utils.toEntity
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
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

    override suspend fun deleteNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity()

        homeLocalDataSource.deleteNote(mappedNote)
    }
}
