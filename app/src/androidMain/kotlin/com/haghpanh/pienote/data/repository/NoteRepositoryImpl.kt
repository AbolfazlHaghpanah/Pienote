package pienote.data.repository

import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel
import pienote.data.dao.CategoryDao
import pienote.data.dao.NoteDao
import pienote.data.utils.toEntity
import pienote.features.note.domain.model.NoteWithCategoryDomainModel
import pienote.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao
) : NoteRepository {
    override fun observeNote(id: Int): Flow<NoteWithCategoryDomainModel> =
        noteDao.observeNoteWithCategoryById(id).map { it.toDomainModel() }

    override suspend fun getCategories(): List<CategoryDomainModel> =
        categoryDao.getCategories().map { it.toDomainModel() }

    override suspend fun insertNote(note: NoteDomainModel): Int =
        noteDao.insertNote(note.toEntity(true)).toInt()

    override suspend fun updateNote(note: NoteDomainModel) =
        noteDao.updateNote(note.toEntity())
}
