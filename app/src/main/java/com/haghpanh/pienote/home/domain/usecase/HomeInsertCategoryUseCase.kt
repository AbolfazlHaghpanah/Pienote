package com.haghpanh.pienote.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeInsertCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(category: CategoryDomainModel) {
        homeRepository.insertCategory(category)
    }
}