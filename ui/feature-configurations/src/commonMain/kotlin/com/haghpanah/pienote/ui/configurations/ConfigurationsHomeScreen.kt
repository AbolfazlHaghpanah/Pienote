package com.haghpanah.pienote.ui.configurations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConfigurationsScreen(navController: NavController) {
    ConfigurationsScreen(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun ConfigurationsScreen(
    navController: NavController,
    viewModel: ConfigurationsViewModel,
) {
    val state by viewModel.collectAsStateWithLifecycle()

    ConfigurationsScreen(
        state = state,
        onBack = { navController.navigateUp() },
        onSetAppLanguage = viewModel::setAppLanguage,
        onSetTheme = viewModel::setTheme
    )
}

@Composable
internal expect fun ConfigurationsScreen(
    state: ConfigurationsViewState,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
    onSetAppLanguage: (SupportedLanguage) -> Unit,
)
