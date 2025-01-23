package com.haghpanah.pienote.usecase.note

import com.eygraber.uri.Uri
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.NoteRepository
import com.haghpanah.pienote.usecase.common.SaveImageUriInCacheUseCase

class NoteUpdateNoteImageUseCase(
    private val noteRepository: NoteRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(note: NoteDomainModel, uri: Uri?): Uri? {
        val newImage = saveImageUriInCacheUseCase(uri)

        noteRepository.updateNote(note.copy(image = newImage?.toString()))
        return newImage
    }
}
