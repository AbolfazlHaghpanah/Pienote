package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.model.NoteWithCategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNote(id: Long): Flow<NoteWithCategoryDomainModel>
    suspend fun getCategories(): List<CategoryDomainModel>
    suspend fun insertNote(note: NoteDomainModel): Long
    suspend fun updateNote(note: NoteDomainModel)
}
