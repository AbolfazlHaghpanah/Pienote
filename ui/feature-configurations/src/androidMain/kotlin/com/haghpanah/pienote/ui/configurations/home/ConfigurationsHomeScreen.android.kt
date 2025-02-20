package com.haghpanah.pienote.ui.configurations.home

import android.annotation.SuppressLint
import android.app.LocaleConfig
import android.app.LocaleManager
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.haghpanah.pienote.baseui.AppLanguage
import com.haghpanah.pienote.designsystem.component.PienoteScaffold
import com.haghpanah.pienote.designsystem.component.PienoteTopBar
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.ui.configurations.home.component.LocalPickerSection
import com.haghpanah.pienote.ui.configurations.home.component.ThemePickerSection
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.configurations
import pienote.ui.base.generated.resources.label_configurations

@SuppressLint("NewApi")
@Composable
internal actual fun ConfigurationsHomeScreen(
    state: ConfigurationsHomeViewState,
    backButtonText: String?,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
) {
    val context = LocalContext.current
    val localManager = context.getSystemService(LocaleManager::class.java)

    SideEffect {
        localManager.overrideLocaleConfig = LocaleConfig(
            LocaleList.forLanguageTags(AppLanguage.entries.joinToString(separator = ",") { it.tag })
        )
    }

    var selectedLanguage: AppLanguage? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(localManager.applicationLocales.get(0)) {
        val currentLocal = localManager.applicationLocales.get(0)
        selectedLanguage = AppLanguage.entries.firstOrNull {
            currentLocal
                ?.toLanguageTag()
                ?.contains(it.tag)
                ?: false
        }
    }

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
        AnimatedContent(
            targetState = Pair(state.isLoading, selectedLanguage),
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
                    state.currentTheme?.let { theme ->
                        ThemePickerSection(
                            onSelectTheme = onSetTheme,
                            selectedTheme = theme
                        )
                    }

                    LocalPickerSection(
                        selectedLanguage = result.second,
                        onLanguageSelected = { lang ->
                            val appLocale = LocaleListCompat.forLanguageTags(lang.tag)
                            AppCompatDelegate.setApplicationLocales(appLocale)
                        }
                    )
                }
            }
        }
    }
}

