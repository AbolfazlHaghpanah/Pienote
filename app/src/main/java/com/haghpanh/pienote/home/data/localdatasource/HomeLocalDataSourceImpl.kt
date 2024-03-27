package com.haghpanh.pienote.home.data.localdatasource

import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.home.data.dao.HomeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val homeDao: HomeDao
) : HomeLocalDataSource {
    override fun observeNotes(): Flow<List<NoteEntity>> =
        homeDao.observeNotes()

    override suspend fun insertNote(note: NoteEntity) =
        homeDao.insertNotes(note)
}