package pienote.features.note.domain.usecase

import pienote.commondomain.model.CategoryDomainModel
import pienote.features.note.domain.repository.NoteRepository

class NoteGetCategoriesUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): List<CategoryDomainModel> =
        noteRepository.getCategories()
}
