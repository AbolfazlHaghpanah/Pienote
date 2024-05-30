package com.haghpanh.pienote.feature_category.data.repository

import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import com.haghpanh.pienote.feature_category.domain.model.CategoryWithNotesDomainModel
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun observeCategory(id: Int): Flow<CategoryWithNotesDomainModel> {
        val result = categoryDao.observeCategory(id)

        return result.map { categoryWithNotes ->
            val category = categoryWithNotes.category

            CategoryWithNotesDomainModel(
                id = category.id,
                name = category.name,
                priority = category.priority,
                image = category.image,
                notes = categoryWithNotes.notes.map { entity -> entity.toDomainModel() }
            )
        }
    }

    override fun observeAvailableNotes(): Flow<List<NoteDomainModel>> =
        categoryDao.observeAvailableNotes().map { note -> note.map { it.toDomainModel() } }


    override suspend fun deleteNoteFromCategory(noteId: Int) {
        categoryDao.deleteNoteFromCategory(noteId)
    }

    override suspend fun updateCategory(categoryDomainModel: CategoryDomainModel) {
        val mappedCategory = categoryDomainModel.toEntity()

        categoryDao.updateCategory(mappedCategory)
    }

    override suspend fun addNoteToCategory(noteId: Int, categoryId: Int) {
        categoryDao.addNoteToCategory(noteId, categoryId)
    }

    private fun CategoryDomainModel.toEntity() =
        CategoryEntity(
            id = id,
            name = name,
            priority = priority,
            image = image
        )
}