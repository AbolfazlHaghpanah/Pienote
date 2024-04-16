package com.haghpanh.pienote.feature_category.data.repository

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_category.data.datasource.CategoryLocalDataSource
import com.haghpanh.pienote.feature_category.domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoryRepository {
    override fun getCategory(id: Int): Flow<CategoryDomainModel> {
        val category = categoryLocalDataSource.getCategoryWithNotes(id)

        return category.map { noteWithCategory ->
            val first = noteWithCategory.first()

            CategoryDomainModel(
                id = first.categoryId,
                name = first.categoryName,
                priority = first.categoryPriority,
                image = first.categoryImage,
                notes = if (first.noteId == null) {
                    emptyList()
                } else {
                    noteWithCategory.map {
                        NoteDomainModel(
                            id = it.noteId ?: -1,
                            title = it.noteTitle.orEmpty(),
                            note = it.content.orEmpty(),
                            addedTime = ""
                        )
                    }
                }
            )
        }
    }
}