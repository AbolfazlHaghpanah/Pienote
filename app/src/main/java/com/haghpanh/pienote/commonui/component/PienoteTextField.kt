package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun PienoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PienoteTheme.typography.displaySmall,
    placeHolder: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = placeHolder,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        textStyle = textStyle
    )
}
