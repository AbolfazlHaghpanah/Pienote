package pienote.features.note.domain.usecase

import pienote.commondomain.model.NoteDomainModel
import pienote.features.note.domain.repository.NoteRepository

class NoteInsertNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteDomainModel): Int =
        noteRepository.insertNote(note)
}
