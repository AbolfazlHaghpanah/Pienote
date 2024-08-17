package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PienoteTopBar(
    title: String,
    icon: Int? = null,
    action: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = PienoteTheme.typography.headlineMedium,
                color = PienoteTheme.colors.onBackground
            )
        },
        colors = TopAppBarColors(
            containerColor = PienoteTheme.colors.background,
            navigationIconContentColor = PienoteTheme.colors.onBackground,
            scrolledContainerColor = PienoteTheme.colors.onBackground,
            titleContentColor = PienoteTheme.colors.onBackground,
            actionIconContentColor = PienoteTheme.colors.onBackground
        ),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        navigationIcon = {
            icon?.let {
                Icon(painter = painterResource(id = icon), contentDescription = null)
            }
        },
        actions = {
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
    )
}

@Composable
fun PienoteTopBar(
    title: String,
    icon: Int? = null,
    parent: String,
    onBack: () -> Unit,
    action: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
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
                    text = parent,
                    style = PienoteTheme.typography.labelLarge
                )
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
                        painter = painterResource(id = iconId),
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
