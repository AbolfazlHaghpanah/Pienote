package com.haghpanh.pienote.features.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@OptIn(ExperimentalMaterial3Api::class)
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
                    style = PienoteTheme.typography.headlineMedium
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = PienoteTheme.typography.headlineMedium,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .heightIn(min = 250.dp)
                .onFocusChanged {
                    isFocusedOnNote = it.isFocused
                },
            value = note,
            onValueChange = onUpdateNote,
            placeholder = {
                Text(
                    text = stringResource(R.string.label_write_here),
                    style = PienoteTheme.typography.bodyMedium
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = PienoteTheme.typography.bodyLarge,
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
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = PienoteTheme.colors.error,
//                    backgroundColor = Color.Transparent
//                )
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
