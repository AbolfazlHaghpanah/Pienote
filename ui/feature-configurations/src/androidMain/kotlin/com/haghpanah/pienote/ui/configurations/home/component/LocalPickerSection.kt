package com.haghpanah.pienote.ui.configurations.home.component

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.baseui.AppLanguage
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.label_language

@SuppressLint("NewApi")
@Composable
internal fun LocalPickerSection(
    selectedLanguage: AppLanguage?,
    onLanguageSelected: (AppLanguage) -> Unit,
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

        AppLanguage.entries.forEach { lang ->
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
                    text = lang.stringResource,
                    style = PienoteTheme.typography.bodyLarge,
                    color = PienoteTheme.colors.onBackground
                )
            }
        }
    }
}