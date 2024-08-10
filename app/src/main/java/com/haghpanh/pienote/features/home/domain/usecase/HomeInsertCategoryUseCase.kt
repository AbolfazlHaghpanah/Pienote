package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.repository.CommonRepository
import javax.inject.Inject

class HomeInsertCategoryUseCase @Inject constructor(
    private val commonRepository: CommonRepository
) {
    suspend operator fun invoke(name: String, image: String?) {
        commonRepository.insertCategory(
            name = name,
            image = image
        )
    }
}
