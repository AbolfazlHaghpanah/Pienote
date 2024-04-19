package com.haghpanh.pienote.feature_category.data.datasource

import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import com.haghpanh.pienote.feature_category.data.relations.CategoryWithNotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun observeCategory(categoryId: Int): Flow<CategoryWithNotes> =
        categoryDao.observeCategory(categoryId)
}