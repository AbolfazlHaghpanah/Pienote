package com.haghpanh.pienote.features.note.domain.usecase

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository

class NoteGetCategoriesUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): List<CategoryDomainModel> =
        noteRepository.getCategories()
}
