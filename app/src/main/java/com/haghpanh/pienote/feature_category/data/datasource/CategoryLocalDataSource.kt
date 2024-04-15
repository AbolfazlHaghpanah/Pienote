package com.haghpanh.pienote.feature_category.data.datasource

import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import com.haghpanh.pienote.feature_category.data.databasviews.NotesWithCategoryView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getCategoryWithNotes(categoryId: Int): Flow<List<NotesWithCategoryView>> =
        categoryDao.getCategoryWithNotes(categoryId)
}