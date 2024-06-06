package com.haghpanh.pienote.common_domain.repository

import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.common_domain.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
}