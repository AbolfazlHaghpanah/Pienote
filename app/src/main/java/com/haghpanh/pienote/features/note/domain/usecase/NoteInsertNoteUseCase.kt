package com.haghpanh.pienote.features.note.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository

class NoteInsertNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteDomainModel): Int =
        noteRepository.insertNote(note)
}
