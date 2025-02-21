package com.haghpanah.pienote.usecase.configurations

import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.repository.ConfigurationsRepository
import kotlinx.coroutines.flow.Flow

class ObserveCurrentLanguageUseCase(
    private val repository: ConfigurationsRepository,
) {
    operator fun invoke(): Flow<SupportedLanguage> = repository.observeAppLanguage()
}