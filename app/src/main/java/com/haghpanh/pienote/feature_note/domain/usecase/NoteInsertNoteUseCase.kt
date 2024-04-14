package com.haghpanh.pienote.feature_note.domain.usecase

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteInsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        noteRepository.insertNote(note)
    }
}