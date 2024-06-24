package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeObserveCategories @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<CategoryDomainModel>> =
        homeRepository.observeCategories()
}