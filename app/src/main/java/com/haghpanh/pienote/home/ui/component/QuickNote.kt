package com.haghpanh.pienote.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.R
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Composable
fun QuickNoteTextField(
    modifier: Modifier = Modifier,
    title: String,
    note: String,
    onUpdateTitle: (String) -> Unit,
    onUpdateNote: (String) -> Unit,
    onDone: () -> Unit,
    onDiscard: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            modifier = modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            value = title,
            onValueChange = onUpdateTitle,
            placeholder = {
                Text(
                    text = stringResource(R.string.label_title),
                    style = PienoteTheme.typography.h2
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = PienoteTheme.typography.h2
        )

        OutlinedTextField(
            modifier = modifier
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .aspectRatio(1.8f),
            value = note,
            onValueChange = onUpdateNote,
            placeholder = {
                Text(
                    text = "Write Here...",
                    style = PienoteTheme.typography.body2
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = PienoteTheme.typography.body1
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(24.dp),
        ) {
            TextButton(
                onClick = onDiscard,
                colors = ButtonDefaults.buttonColors(
                    contentColor = PienoteTheme.colors.error,
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(text = "Discard")
            }

            Spacer(modifier = modifier.width(16.dp))

            TextButton(
                onClick = onDone
            ) {
                Text(text = "Done")
            }

        }
    }
}

@Composable
fun QuickNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.secondary,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = "Quick Note",
            style = PienoteTheme.typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSecondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .aspectRatio(0.4f)
                .fillMaxHeight(),
            imageVector = Icons.Rounded.Add,
            contentDescription = "Quick Note"
        )
    }
}
