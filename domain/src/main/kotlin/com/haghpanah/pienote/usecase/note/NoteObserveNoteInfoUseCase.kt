package com.haghpanah.pienote.usecase.note

import com.haghpanah.pienote.model.NoteWithCategoryDomainModel
import com.haghpanah.pienote.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteObserveNoteInfoUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(id: Long): Flow<NoteWithCategoryDomainModel> =
        noteRepository.observeNote(id)
}
