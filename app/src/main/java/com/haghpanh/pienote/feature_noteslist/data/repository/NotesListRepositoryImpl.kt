package com.haghpanh.pienote.feature_noteslist.data.repository

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesListDao
import com.haghpanh.pienote.feature_noteslist.domain.repository.NotesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesListRepositoryImpl @Inject constructor(
    private val notesListDao: NotesListDao
) : NotesListRepository {
    override fun observeNoteList(): Flow<List<NoteDomainModel>> =
        notesListDao.observeNotes().map { it.map { it.toDomainModel() } }
}