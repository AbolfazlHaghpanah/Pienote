package com.haghpanah.pienote.ui.configurations.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.haghpanah.pienote.model.ThemeType
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ConfigurationsHomeScreen(navController: NavController) {
    ConfigurationsHomeScreen(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun ConfigurationsHomeScreen(
    navController: NavController,
    viewModel: ConfigurationsHomeViewModel,
) {
    val state by viewModel.collectAsStateWithLifecycle()

    ConfigurationsHomeScreen(
        state = state,
        backButtonText = navController
            .currentBackStackEntry
            ?.arguments
            ?.getString("backButtonText"),
        onBack = { navController.navigateUp() },
        onSetTheme = viewModel::setTheme
    )
}

@Composable
internal expect fun ConfigurationsHomeScreen(
    state: ConfigurationsHomeViewState,
    backButtonText: String?,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
)
