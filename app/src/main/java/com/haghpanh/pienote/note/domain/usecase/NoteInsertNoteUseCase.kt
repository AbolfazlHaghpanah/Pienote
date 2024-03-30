package com.haghpanh.pienote.note.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteInsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        noteRepository.insertNote(note)
    }
}