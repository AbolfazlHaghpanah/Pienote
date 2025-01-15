package com.haghpanah.pienote.usecase.note

import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.NoteRepository

class NoteUpdateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteDomainModel: NoteDomainModel) {
        noteRepository.updateNote(noteDomainModel)
    }
}
