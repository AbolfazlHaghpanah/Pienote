package com.haghpanah.pienote.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.haghpanah.pienote.database.PienoteDatabase
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.CategoryWithNotesDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.utils.noteMapper
import com.haghpanah.pienote.utils.toCategoryDomainModel
import com.haghpanah.pienote.utils.toNoteDomainModel
import com.haghpanah.pienote.utils.toSqlCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class CategoryRepositoryImpl(
    private val database: PienoteDatabase
) : CategoryRepository {
    override fun observeCategory(id: Long): Flow<CategoryWithNotesDomainModel> =
        database.categoriesQueries
            .getCategoryById(id)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapNotNull { list ->
                val category = list.first().toCategoryDomainModel()
                val notes = list.map { it.toNoteDomainModel() }

                CategoryWithNotesDomainModel(
                    id = category.id,
                    name = category.name,
                    priority = category.priority,
                    image = category.image,
                    notes = notes
                )
            }

    override fun observeAvailableNotes(): Flow<List<NoteDomainModel>> =
        database.noteQueries
            .getNotesWithoutCategory(
                mapper = ::noteMapper
            )
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun deleteNoteFromCategory(noteId: List<Long>) {
        database.noteQueries.nullCategoryId(noteId)
    }

    override suspend fun updateCategory(categoryDomainModel: CategoryDomainModel) {
        database.categoriesQueries.insertOrReplaceCategory(
            categoryDomainModel.toSqlCategories()
        )
    }

    override suspend fun addNoteToCategory(noteId: Long, categoryId: Long) {
        database.noteQueries.addNotesToCategory(
            categoryId = categoryId,
            noteIds = listOf(noteId)
        )
    }
}