package com.haghpanah.pienote.ui.configurations.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanah.pienote.designsystem.component.PienoteScaffold
import com.haghpanah.pienote.designsystem.component.PienoteTopBar
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.ui.configurations.home.component.ThemePickerSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.configurations
import pienote.ui.base.generated.resources.label_configurations

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
private fun ConfigurationsHomeScreen(
    state: ConfigurationsHomeViewState,
    backButtonText: String?,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
) {
    PienoteScaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            PienoteTopBar(
                title = stringResource(Res.string.label_configurations),
                icon = Res.drawable.configurations,
                backButtonText = backButtonText,
                onBack = onBack
            )
        }
    ) {
        AnimatedContent(state.isLoading) { isLoading ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        strokeWidth = 2.dp,
                        strokeCap = StrokeCap.Round
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(28.dp)
                ) {
                    state.currentTheme?.let { theme ->
                        ThemePickerSection(
                            onSelectTheme = onSetTheme,
                            selectedTheme = theme
                        )
                    }
                }
            }
        }
    }
}
