package com.haghpanh.pienote.feature_home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.R
import com.haghpanh.pienote.common_ui.theme.PienoteTheme

@Composable
fun QuickNoteTextField(
    modifier: Modifier = Modifier,
    title: String,
    note: String,
    onUpdateTitle: (String) -> Unit,
    onUpdateNote: (String) -> Unit,
    onDone: () -> Unit,
    onDiscard: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        var isFocusedOnNote by remember {
            mutableStateOf(false)
        }

        SideEffect {
            if (!isFocusedOnNote) {
                focusRequester.requestFocus()
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .focusRequester(focusRequester)
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
            textStyle = PienoteTheme.typography.h2,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .weight(1f)
                .onFocusChanged {
                    isFocusedOnNote = it.isFocused
                },
            value = note,
            onValueChange = onUpdateNote,
            placeholder = {
                Text(
                    text = stringResource(R.string.label_write_here),
                    style = PienoteTheme.typography.body2
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = PienoteTheme.typography.body1,
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(24.dp),
        ) {
            TextButton(
                onClick = onDiscard,
                colors = ButtonDefaults.buttonColors(
                    contentColor = PienoteTheme.colors.error,
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(text = stringResource(R.string.label_discard))
            }

            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = onDone
            ) {
                Text(text = stringResource(R.string.label_done))
            }
        }
    }
}
