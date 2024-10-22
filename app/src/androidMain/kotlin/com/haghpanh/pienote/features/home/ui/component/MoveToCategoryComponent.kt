package pienote.features.home.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pienote.commonui.theme.PienoteTheme
import pienote.features.home.ui.Category

@Composable
fun MoveToCategoryComponent(
    modifier: Modifier = Modifier,
    onCategorySelected: (Int) -> Unit,
    categories: List<List<Category>>?,
    onDiscard: () -> Unit
) {
    BackHandler(onBack = onDiscard)

    Column(
        modifier
            .clip(PienoteTheme.shapes.large)
            .background(PienoteTheme.colors.surfaceContainerHighest)
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = SelectingNoteOptions.MoveToCategory.label,
            style = PienoteTheme.typography.headlineMedium,
            color = PienoteTheme.colors.onSurface
        )

        HorizontalDivider()

        categories
            ?.flatten()
            ?.forEach { cat ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(PienoteTheme.shapes.small)
                        .clickable {
                            onCategorySelected(cat.id)
                        }
                        .padding(4.dp),
                    text = cat.name,
                    style = PienoteTheme.typography.titleMedium,
                    color = PienoteTheme.colors.onSurface
                )
            }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onDiscard,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = PienoteTheme.colors.onErrorContainer
            ),
            border = BorderStroke(
                width = 1.dp,
                color = PienoteTheme.colors.onErrorContainer
            )
        ) {
            Text(text = "Discard")
        }
    }
}
