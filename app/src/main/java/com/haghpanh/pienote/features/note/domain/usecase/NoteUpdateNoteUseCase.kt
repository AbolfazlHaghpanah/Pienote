package com.haghpanh.pienote.features.note.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository

class NoteUpdateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteDomainModel: NoteDomainModel) {
        noteRepository.updateNote(noteDomainModel)
    }
}
