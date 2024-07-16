package com.haghpanh.pienote.features.library.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun LibsItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(PienoteTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(34.dp),
            painter = painterResource(id = icon),
            contentDescription = title
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = PienoteTheme.typography.headlineSmall
        )
    }
}
