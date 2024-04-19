package com.haghpanh.pienote.feature_category.domain.repository

import com.haghpanh.pienote.feature_category.domain.model.CategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategory(id: Int): Flow<CategoryDomainModel>
}