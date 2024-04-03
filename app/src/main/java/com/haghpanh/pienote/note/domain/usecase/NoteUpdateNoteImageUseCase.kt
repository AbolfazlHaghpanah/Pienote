package com.haghpanh.pienote.note.domain.usecase

import android.net.Uri
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteUpdateNoteImageUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val noteSaveImageUriInCacheUseCase: NoteSaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(note: NoteDomainModel, uri: Uri?) {
        val newImage = noteSaveImageUriInCacheUseCase(uri)?.toString()

        noteRepository.updateNote(note.copy(image = newImage))
    }
}