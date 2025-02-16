package com.haghpanah.pienote.ui.configurations.home

import androidx.lifecycle.viewModelScope
import com.haghpanah.pienote.baseui.BaseViewModel
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.usecase.common.ObserveThemeUseCase
import com.haghpanah.pienote.usecase.common.SetThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigurationsHomeViewModel(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
) : BaseViewModel<ConfigurationsHomeViewState>(
    initialState = ConfigurationsHomeViewState()
) {
    init {
        observeCurrentTheme()
    }

    fun setTheme(themeType: ThemeType) {
        viewModelScope.launch(Dispatchers.IO) {
            setThemeUseCase(themeType = themeType)
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