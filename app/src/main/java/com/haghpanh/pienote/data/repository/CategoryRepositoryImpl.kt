package com.haghpanh.pienote.data.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.features.category.domain.model.CategoryWithNotesDomainModel
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val noteDao: NoteDao
) : CategoryRepository {
    override fun observeCategory(id: Int): Flow<CategoryWithNotesDomainModel> {
        val result = categoryDao.observeCategoryById(id)

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
        noteDao.observeNotes().map { note -> note.map { it.toDomainModel() } }

    override suspend fun deleteNoteFromCategory(noteId: Int) {
        noteDao.deleteNoteFromCategory(noteId)
    }

    override suspend fun updateCategory(categoryDomainModel: CategoryDomainModel) {
        val mappedCategory = categoryDomainModel.toEntity()

        categoryDao.updateCategory(mappedCategory)
    }

    override suspend fun addNoteToCategory(noteId: Int, categoryId: Int) {
        noteDao.addNoteToCategory(noteId, categoryId)
    }

    private fun CategoryDomainModel.toEntity() =
        CategoryEntity(
            id = id,
            name = name,
            priority = priority,
            image = image
        )
}
