package com.haghpanah.pienote.ui.configurations

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haghpanah.pienote.baseui.BaseViewModel
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.usecase.configurations.ObserveCurrentLanguageUseCase
import com.haghpanah.pienote.usecase.configurations.ObserveThemeUseCase
import com.haghpanah.pienote.usecase.configurations.SetAppLanguageUseCase
import com.haghpanah.pienote.usecase.configurations.SetThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConfigurationsViewModel(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val observeCurrentLanguageUseCase: ObserveCurrentLanguageUseCase,
    private val setAppLanguageUseCase: SetAppLanguageUseCase,
) : BaseViewModel<ConfigurationsViewState>(
    initialState = ConfigurationsViewState()
) {
    init {
        observeCurrentTheme()
        observeCurrentLanguage()
    }

    fun setTheme(themeType: ThemeType) {
        viewModelScope.launch(Dispatchers.IO) {
            setThemeUseCase(themeType = themeType)
        }
    }

    fun setAppLanguage(language: SupportedLanguage) {
        viewModelScope.launch(Dispatchers.IO) {
            setAppLanguageUseCase(language)
        }
    }

    private fun observeCurrentLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            observeCurrentLanguageUseCase()
                .collect {
                    updateState {
                        copy(currentLanguage = it)
                    }
                }
        }
    }

    private fun observeCurrentTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            observeThemeUseCase().collect {
                updateState {
                    copy(currentTheme = it)
                }
            }
        }
    }
}
