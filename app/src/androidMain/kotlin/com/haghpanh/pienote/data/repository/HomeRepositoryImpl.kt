package pienote.data.repository

import pienote.commondomain.model.NoteDomainModel
import pienote.data.dao.CategoryDao
import pienote.data.dao.NoteDao
import pienote.data.utils.toEntity
import pienote.features.home.domain.model.CategoryWithNotesCountDomainModel
import pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
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

    override fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>> {
        val categoriesFlow = categoryDao.observeCategoriesWithNotesCount()

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
