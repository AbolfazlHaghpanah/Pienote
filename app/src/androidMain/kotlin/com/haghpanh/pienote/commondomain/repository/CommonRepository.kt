package pienote.commondomain.repository

import pienote.commondomain.model.NoteDomainModel

interface CommonRepository {
    suspend fun insertNote(note: NoteDomainModel)
    suspend fun insertCategory(name: String, image: String?)
    suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int)
}
