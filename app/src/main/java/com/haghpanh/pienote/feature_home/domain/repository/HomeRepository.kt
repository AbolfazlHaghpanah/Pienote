package com.haghpanh.pienote.feature_home.domain.repository

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_home.domain.model.QuickNoteDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes(): Flow<List<NoteDomainModel>>
    fun observeCategories(): Flow<List<CategoryDomainModel>>
    fun observeNotesByCategory(categoryId : Int): Flow<List<NoteDomainModel>>
    suspend fun insertNote(note: QuickNoteDomainModel)
    suspend fun insertCategory(category: CategoryDomainModel)
    suspend fun deleteNote(note: NoteDomainModel)
}