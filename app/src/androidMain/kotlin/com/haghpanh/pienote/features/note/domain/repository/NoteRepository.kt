package pienote.features.note.domain.repository

import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel
import pienote.features.note.domain.model.NoteWithCategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNote(id: Int): Flow<NoteWithCategoryDomainModel>
    suspend fun getCategories(): List<CategoryDomainModel>
    suspend fun insertNote(note: NoteDomainModel): Int
    suspend fun updateNote(note: NoteDomainModel)
}
