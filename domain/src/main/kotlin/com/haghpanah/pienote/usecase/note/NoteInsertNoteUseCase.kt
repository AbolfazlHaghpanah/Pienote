package com.haghpanah.pienote.usecase.note

import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.NoteRepository

class NoteInsertNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteDomainModel): Long =
        noteRepository.insertNote(note)
}