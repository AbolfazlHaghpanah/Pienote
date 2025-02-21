package com.haghpanah.pienote.ui.configurations.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.model.SupportedLanguage.En
import com.haghpanah.pienote.model.SupportedLanguage.Fa
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.label_English
import pienote.ui.base.generated.resources.label_language
import pienote.ui.base.generated.resources.label_persian

@Composable
internal fun LocalPickerSection(
    selectedLanguage: SupportedLanguage?,
    onLanguageSelected: (SupportedLanguage) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.label_language),
            style = PienoteTheme.typography.bodyLarge,
            color = PienoteTheme.colors.onBackground
        )

        SupportedLanguage.entries.forEach { lang ->
            Box(
                modifier = Modifier
                    .clip(PienoteTheme.shapes.medium)
                    .height(64.dp)
                    .clickable {
                        onLanguageSelected(lang)
                    }
                    .fillMaxWidth()
                    .background(
                        if (selectedLanguage == lang) {
                            PienoteTheme.colors.surfaceContainerHighest
                        } else {
                            PienoteTheme.colors.surfaceContainerLowest
                        }
                    ),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = lang.getText(),
                    style = PienoteTheme.typography.bodyLarge,
                    color = PienoteTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
private fun SupportedLanguage.getText(): String =
    when (this) {
        En -> stringResource(Res.string.label_English)
        Fa -> stringResource(Res.string.label_persian)
    }