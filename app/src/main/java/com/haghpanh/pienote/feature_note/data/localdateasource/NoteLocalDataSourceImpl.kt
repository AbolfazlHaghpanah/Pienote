package com.haghpanh.pienote.feature_note.data.localdateasource

import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_note.data.dao.NoteDao
import com.haghpanh.pienote.feature_note.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun observeNote(id: Int): Flow<NoteWithCategory> =
        noteDao.observeNote(id)

    override fun getCategories(): List<CategoryEntity> =
        noteDao.getCategories()

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }
}