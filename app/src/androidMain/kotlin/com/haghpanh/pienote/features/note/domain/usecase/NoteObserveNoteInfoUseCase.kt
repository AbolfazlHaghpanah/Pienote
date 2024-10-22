package pienote.features.note.domain.usecase

import pienote.features.note.domain.model.NoteWithCategoryDomainModel
import pienote.features.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteObserveNoteInfoUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Flow<NoteWithCategoryDomainModel> =
        noteRepository.observeNote(id)
}
