package com.haghpanh.pienote.note.domain.usecase

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteGetCategoriesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() : List<CategoryDomainModel> =
        noteRepository.getCategories()
}