package com.haghpanh.pienote.data.repository

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.data.utils.toEntity

class CommonRepositoryImpl (
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
