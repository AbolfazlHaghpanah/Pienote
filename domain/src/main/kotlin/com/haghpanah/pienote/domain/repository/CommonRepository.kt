package com.haghpanah.pienote.domain.repository

import com.haghpanah.pienote.domain.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
    suspend fun insertCategory(name: String, image: String?)
    suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int)
}