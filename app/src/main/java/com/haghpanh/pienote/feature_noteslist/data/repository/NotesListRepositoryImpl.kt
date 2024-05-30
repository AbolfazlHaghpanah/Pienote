package com.haghpanh.pienote.feature_noteslist.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesListDao
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesResult
import com.haghpanh.pienote.feature_noteslist.domain.repository.NotesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NotesListRepositoryImpl @Inject constructor(
    private val notesListDao: NotesListDao
) : NotesListRepository {
    override fun observeNoteList(): Flow<List<NoteDomainModel>> {

        notesListDao.getPagedNotes()
        return notesListDao.observeNotes()
            .onEach { Log.d("Jai", "observeNoteList: ${it.joinToString()}") }
            .map { it.map { it.toDomainModel() } }
    }

    override fun getPagedNoteList(): Flow<PagingData<NotesResult>> = Pager(
        config = PagingConfig(
            pageSize = 23,
        ),
        pagingSourceFactory = { notesListDao.getPagedNotes() }
    ).flow
}