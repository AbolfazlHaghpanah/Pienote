package com.haghpanh.pienote.feature_note.domain.usecase

import android.net.Uri
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_domain.usecase.SaveImageUriInCacheUseCase
import com.haghpanh.pienote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteUpdateNoteImageUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(note: NoteDomainModel, uri: Uri?): Uri? {
        val newImage = saveImageUriInCacheUseCase(uri)

        noteRepository.updateNote(note.copy(image = newImage?.toString()))
        return newImage
    }
}