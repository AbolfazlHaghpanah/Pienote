package com.haghpanh.pienote.feature_noteslist.domain.repository

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow


interface NotesListRepository {
    fun observeNoteList() : Flow<List<NoteDomainModel>>
}