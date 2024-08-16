package com.haghpanh.pienote.features.home.domain.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes(): Flow<List<NoteDomainModel>>
    fun observeCategories(): Flow<List<CategoryDomainModel>>
    fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>>
    suspend fun deleteNote(note: NoteDomainModel)
}
