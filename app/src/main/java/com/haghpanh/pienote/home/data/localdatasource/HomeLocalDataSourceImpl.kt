package com.haghpanh.pienote.home.data.localdatasource

import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondata.relation.NoteWithCategory
import com.haghpanh.pienote.home.data.dao.HomeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val homeDao: HomeDao
) : HomeLocalDataSource {
    override fun observeNotes(): Flow<List<NoteEntity>> =
        homeDao.observeNotes()

    override fun observeCategories(): Flow<List<CategoryEntity>> =
        homeDao.observeCategories()

    override fun observeNoteByCategory(categoryId: Int): Flow<List<NoteEntity>> =
        homeDao.observeNoteByCategory(categoryId)

    override suspend fun insertNote(note: NoteEntity) =
        homeDao.insertNotes(note)

    override suspend fun insertCategory(category: CategoryEntity) =
        homeDao.insertCategory(category)
}