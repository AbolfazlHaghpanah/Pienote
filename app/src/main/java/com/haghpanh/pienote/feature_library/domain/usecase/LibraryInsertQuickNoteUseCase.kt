package com.haghpanh.pienote.feature_library.domain.usecase

import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.common_domain.repository.CommonRepository
import com.haghpanh.pienote.feature_library.domain.model.QuickNoteDomainModel
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
            priority = null
        )
}