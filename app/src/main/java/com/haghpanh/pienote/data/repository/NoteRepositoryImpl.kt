package com.haghpanh.pienote.data.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.utils.toEntity
import com.haghpanh.pienote.features.note.domain.model.NoteWithCategoryDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository
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
