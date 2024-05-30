package com.haghpanh.pienote.feature_category.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.common_ui.theme.PienoteTheme

@Composable
fun CategoryDialogItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(PienoteTheme.shapes.small)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = icon),
            contentDescription = "edit"
        )

        Text(
            text = title,
            style = PienoteTheme.typography.subtitle1,
            color = PienoteTheme.colors.onSurface
        )
    }
}