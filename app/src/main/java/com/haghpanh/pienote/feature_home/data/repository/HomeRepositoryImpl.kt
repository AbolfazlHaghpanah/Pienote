package com.haghpanh.pienote.feature_home.data.repository

import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.feature_library.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.feature_home.domain.repository.HomeRepository
import com.haghpanh.pienote.common_data.utils.toEntity
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

    override suspend fun insertCategory(category: CategoryDomainModel) {
        val mappedCategory = category.toEntity()

        homeLocalDataSource.insertCategory(mappedCategory)
    }

    override suspend fun deleteNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity()

        homeLocalDataSource.deleteNote(mappedNote)
    }
}