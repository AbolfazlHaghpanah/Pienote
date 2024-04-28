package com.haghpanh.pienote.feature_noteslist.domain.usecases

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_noteslist.domain.repository.NotesListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesListObserveNotesUseCase @Inject constructor(
    private val notesListRepository: NotesListRepository
) {
    operator fun invoke() : Flow<List<NoteDomainModel>> =
        notesListRepository.observeNoteList()
}