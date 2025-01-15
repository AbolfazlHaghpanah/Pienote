package com.haghpanah.pienote.usecase.note

import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.repository.NoteRepository

class NoteGetCategoriesUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): List<CategoryDomainModel> =
        noteRepository.getCategories()
}
