package com.haghpanh.pienote.features.note.domain.usecase

import com.haghpanh.pienote.features.note.domain.model.NoteWithCategoryDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteObserveNoteInfoUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Flow<NoteWithCategoryDomainModel> =
        noteRepository.observeNote(id)
}
