package com.haghpanah.pienote.ui

import androidx.lifecycle.viewModelScope
import com.haghpanah.pienote.baseui.BaseViewModel
import com.haghpanah.pienote.usecase.configurations.ObserveThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val observeThemeUseCase: ObserveThemeUseCase,
) : BaseViewModel<MainViewState>(
    MainViewState()
) {
    init {
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            observeThemeUseCase().collect { theme ->
                updateState {
                    copy(currentTheme = theme)
                }
            }
        }
    }
}