package pienote.data.repository

import pienote.commondomain.model.NoteDomainModel
import pienote.commondomain.repository.CommonRepository
import pienote.data.dao.CategoryDao
import pienote.data.dao.NoteDao
import pienote.data.entity.CategoryEntity
import pienote.data.utils.toEntity

class CommonRepositoryImpl(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao
) : CommonRepository {
    override suspend fun insertNote(note: NoteDomainModel) {
        noteDao.insertNote(note.toEntity(true))
    }

    override suspend fun insertCategory(name: String, image: String?) {
        categoryDao.insertCategory(
            CategoryEntity(
                name = name,
                priority = null,
                image = image
            )
        )
    }

    override suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int) {
        noteDao.changeNotesCategory(
            noteIds = noteIds,
            categoryId = categoryId
        )
    }
}
