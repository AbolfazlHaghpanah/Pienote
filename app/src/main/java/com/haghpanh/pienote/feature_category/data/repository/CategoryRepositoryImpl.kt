package com.haghpanh.pienote.feature_category.data.repository

import com.haghpanh.pienote.feature_category.data.datasource.CategoryLocalDataSource
import com.haghpanh.pienote.feature_category.domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoryRepository {
    override fun observeCategory(id: Int): Flow<CategoryDomainModel> {
        val result = categoryLocalDataSource.observeCategory(id)

        return result.map { categoryWithNotes ->
            val category = categoryWithNotes.category

            CategoryDomainModel(
                id = category.id,
                name = category.name,
                priority = category.priority,
                image = category.image,
                notes = categoryWithNotes.notes.map { entity -> entity.toDomainModel() }
            )
        }
    }
}