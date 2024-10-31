package com.haghpanah.pienote.usecase.note

import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.NoteRepository

class NoteUpdateNoteImageUseCase(
    private val noteRepository: NoteRepository,
//    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {

    //TODO Check URI
    suspend operator fun invoke(note: NoteDomainModel, uri: String?): String? {
        val newImage = ""//saveImageUriInCacheUseCase(uri)

        noteRepository.updateNote(note.copy(image = newImage?.toString()))
        return newImage
    }
}