package com.haghpanah.pienote.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun PienoteTopBar(
    title: String,
    icon: DrawableResource? = null,
    backButtonText: String? = null,
    onBack: (() -> Unit)? = null,
    actionIcon: DrawableResource? = null,
    action: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        if (backButtonText != null && onBack != null) {
            PienoteChip(
                onClick = onBack
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")

                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = backButtonText,
                        style = PienoteTheme.typography.labelLarge
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                icon?.let { iconId ->
                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(48.dp),
                        painter = painterResource(iconId),
                        contentDescription = title,
                        tint = PienoteTheme.colors.onBackground
                    )
                } ?: Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = title,
                    style = PienoteTheme.typography.headlineMedium,
                    color = PienoteTheme.colors.onBackground
                )
            }

            action?.let {
                PienoteChip(
                    modifier = Modifier
                        .padding(8.dp),
                    shape = PienoteTheme.shapes.rounded,
                    onClick = action,
                    content = {
                        Icon(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxHeight(),
                            imageVector = actionIcon
                                ?.let { vectorResource(it) }
                                ?: Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = PienoteTheme.colors.onBackground
                        )
                    }
                )
            }
        }
    }
}
