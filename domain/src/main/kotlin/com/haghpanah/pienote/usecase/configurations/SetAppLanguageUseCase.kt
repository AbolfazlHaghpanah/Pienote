package com.haghpanah.pienote.usecase.configurations

import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.repository.ConfigurationsRepository

class SetAppLanguageUseCase(
    private val repository: ConfigurationsRepository,
) {
    suspend operator fun invoke(language: SupportedLanguage) {
        repository.setAppLanguage(language)
    }
}