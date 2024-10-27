package com.haghpanah.pienote.coreui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.haghpanah.pienote.coreui.theme.PienoteTheme

import org.jetbrains.compose.resources.stringResource
import pienote.app.generated.resources.Res
import pienote.app.generated.resources.label_discard

@Composable
fun PienoteDialog(
    titleSection: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    onDismissRequest: () -> Unit,
    image: Any? = null
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .aspectRatio(0.8f)
                .background(
                    shape = PienoteTheme.shapes.huge,
                    color = PienoteTheme.colors.surface
                )
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                image?.let {
                    AsyncImage(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .clip(PienoteTheme.shapes.large)
                            .size(82.dp),
                        model = image,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop
                    )
                }

                titleSection()
            }

            Divider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            content()

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(Res.string.label_discard))
            }
        }
    }
}

@Composable
fun PienoteDialog(
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .aspectRatio(0.8f)
                .background(
                    shape = PienoteTheme.shapes.huge,
                    color = PienoteTheme.colors.surface
                )
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            content()
        }
    }
}
