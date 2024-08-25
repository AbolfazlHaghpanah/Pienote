package com.haghpanh.pienote.features.library.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.features.library.domain.model.QuickNoteDomainModel
import javax.inject.Inject

class LibraryInsertQuickNoteUseCase @Inject constructor(
    private val commonRepository: CommonRepository
) {
    suspend operator fun invoke(note: QuickNoteDomainModel) {
        commonRepository.insertNote(note.toNoteDomainModel())
    }

    private fun QuickNoteDomainModel.toNoteDomainModel(): NoteDomainModel =
        NoteDomainModel(
            id = -1,
            title = title.orEmpty(),
            note = note.orEmpty(),
            image = null,
            addedTime = addedTime,
            lastChangedTime = null,
            categoryId = null,
            color = null
        )
}
