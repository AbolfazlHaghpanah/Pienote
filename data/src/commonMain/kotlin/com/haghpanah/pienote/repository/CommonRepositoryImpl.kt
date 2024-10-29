package com.haghpanah.pienote.repository

import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.domain.model.NoteDomainModel
import com.haghpanah.pienote.domain.repository.CommonRepository

class CommonRepositoryImpl(
    private val database: PienoteDatabase
) : CommonRepository {
    override suspend fun insertNote(note: NoteDomainModel) {
        database.noteQueries.addItem()
    }

    override suspend fun insertCategory(name: String, image: String?) {
        database.categoriesQueries.addMockCategories()
        //TODO Implement
    }

    override suspend fun addNotesToCategory(noteIds: List<Int>, categoryId: Int) {
        database.noteQueries.addItem()
    }
}