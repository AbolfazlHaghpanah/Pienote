package com.haghpanh.pienote.common_ui.component

import android.media.Image
import android.net.Uri
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import com.haghpanh.pienote.common_ui.theme.PienoteTheme

@Composable
fun PienoteDialog(
    titleSection: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    onDismissRequest: () -> Unit,
    image: Uri? = null
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
                Text(text = stringResource(id = R.string.label_discard))
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