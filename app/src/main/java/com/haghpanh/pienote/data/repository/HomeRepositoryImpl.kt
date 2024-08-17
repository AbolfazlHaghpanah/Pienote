package com.haghpanh.pienote.data.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.utils.toEntity
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao
) : HomeRepository {
    override fun observeNotes(): Flow<List<NoteDomainModel>> {
        val notesFlow = noteDao.observeNoCategoryNotes()

        return notesFlow.map { notes ->
            notes.map { note ->
                note.toDomainModel()
            }
        }
    }

    override fun observeCategories(): Flow<List<CategoryDomainModel>> {
        val categoriesFlow = categoryDao.observeCategories()

        return categoriesFlow.map { categories ->
            categories.map { category ->
                category.toDomainModel()
            }
        }
    }

    override fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>> {
        val notesFlow = noteDao.observeNoteByCategoryId(categoryId)

        return notesFlow.map { notes ->
            notes.map { note ->
                note.toDomainModel()
            }
        }
    }

    override suspend fun deleteNote(note: NoteDomainModel) {
        val mappedNote = note.toEntity()

        noteDao.deleteNote(mappedNote)
    }
}
