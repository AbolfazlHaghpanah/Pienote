package com.haghpanh.pienote.features.noteslist.domain.repository

import androidx.paging.PagingData
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.noteslist.data.dao.NotesResult
import kotlinx.coroutines.flow.Flow

interface NotesListRepository {
    fun observeNoteList(): Flow<List<NoteDomainModel>>
    fun getPagedNoteList(): Flow<PagingData<NotesResult>>
}
