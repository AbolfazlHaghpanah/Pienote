package com.haghpanh.pienote.note.data.localdateasource

import androidx.room.Insert
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondata.relation.NoteWithCategory
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.note.data.dao.NoteDao
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