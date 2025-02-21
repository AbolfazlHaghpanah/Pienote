package com.haghpanah.pienote.ui.configurations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.designsystem.component.PienoteScaffold
import com.haghpanah.pienote.designsystem.component.PienoteTopBar
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.ui.configurations.component.LocalPickerSection
import com.haghpanah.pienote.ui.configurations.component.ThemePickerSection
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.configurations
import pienote.ui.base.generated.resources.label_configurations
import pienote.ui.base.generated.resources.label_home

@Composable
internal actual fun ConfigurationsScreen(
    state: ConfigurationsViewState,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
    onSetAppLanguage: (SupportedLanguage) -> Unit,
) {
    PienoteScaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            PienoteTopBar(
                title = stringResource(Res.string.label_configurations),
                icon = Res.drawable.configurations,
                backButtonText = stringResource(Res.string.label_home),
                onBack = onBack
            )
        }
    ) {
        AnimatedContent(
            targetState = Pair(state.isLoading, state.currentLanguage),
            label = "screen content",
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            }
        ) { result ->
            if (result.first) {
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
                        .padding(28.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ThemePickerSection(
                        onSelectTheme = onSetTheme,
                        selectedTheme = state.currentTheme!!
                    )

                    LocalPickerSection(
                        selectedLanguage = result.second,
                        onLanguageSelected = onSetAppLanguage
                    )
                }
            }
        }
    }
}

