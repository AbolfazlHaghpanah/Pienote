package pienote.features.note.domain.usecase

import pienote.commondomain.model.NoteDomainModel
import pienote.features.note.domain.repository.NoteRepository

class NoteUpdateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteDomainModel: NoteDomainModel) {
        noteRepository.updateNote(noteDomainModel)
    }
}
