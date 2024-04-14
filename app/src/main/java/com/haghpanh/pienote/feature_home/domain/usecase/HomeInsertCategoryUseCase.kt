package com.haghpanh.pienote.feature_home.domain.usecase

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeInsertCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(category: CategoryDomainModel) {
        homeRepository.insertCategory(category)
    }
}