package com.haghpanh.pienote.feature_note.domain.usecase

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class NoteGetCategoriesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() : List<CategoryDomainModel> =
        noteRepository.getCategories()
}