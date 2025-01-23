package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
    suspend fun insertCategory(name: String, image: String?)
    suspend fun addNotesToCategory(noteIds: List<Long>, categoryId: Long)
}
