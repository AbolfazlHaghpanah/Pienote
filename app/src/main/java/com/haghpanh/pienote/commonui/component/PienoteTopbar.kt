package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PienoteTopBar(
    title: String,
    icon: Int? = null,
    action: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        backgroundColor = PienoteTheme.colors.background,
        contentColor = PienoteTheme.colors.onBackground,
        elevation = 0.dp
    ) {
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
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        painter = painterResource(id = iconId),
                        contentDescription = title,
                        tint = PienoteTheme.colors.onBackground
                    )
                } ?: Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = title,
                    style = PienoteTheme.typography.h2,
                    color = PienoteTheme.colors.onBackground
                )
            }

            action?.let {
                PienoteChip(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f),
                    shape = PienoteTheme.shapes.rounded,
                    onClick = action,
                    content = {
                        Icon(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxSize(),
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = PienoteTheme.colors.onBackground
                        )
                    }
                )
            }
        }
    }
}
