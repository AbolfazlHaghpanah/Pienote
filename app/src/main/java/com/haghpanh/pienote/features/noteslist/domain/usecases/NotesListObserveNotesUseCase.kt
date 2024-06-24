package com.haghpanh.pienote.features.noteslist.domain.usecases

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.noteslist.domain.repository.NotesListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesListObserveNotesUseCase @Inject constructor(
    private val notesListRepository: NotesListRepository
) {
    operator fun invoke() : Flow<List<NoteDomainModel>> =
        notesListRepository.observeNoteList()
}