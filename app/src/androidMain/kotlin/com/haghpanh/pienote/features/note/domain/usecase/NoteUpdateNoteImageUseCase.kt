package pienote.features.note.domain.usecase

import android.net.Uri
import pienote.commondomain.model.NoteDomainModel
import pienote.commondomain.usecase.SaveImageUriInCacheUseCase
import pienote.features.note.domain.repository.NoteRepository

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
