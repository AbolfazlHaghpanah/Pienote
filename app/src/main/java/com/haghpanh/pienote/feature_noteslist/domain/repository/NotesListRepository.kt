package com.haghpanh.pienote.feature_noteslist.domain.repository

import androidx.paging.PagingData
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesResult
import kotlinx.coroutines.flow.Flow


interface NotesListRepository {
    fun observeNoteList() : Flow<List<NoteDomainModel>>
    fun getPagedNoteList() : Flow<PagingData<NotesResult>>
}