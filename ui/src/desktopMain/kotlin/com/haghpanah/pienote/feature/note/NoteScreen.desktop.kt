package com.haghpanah.pienote.feature.note

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eygraber.uri.Uri
import com.haghpanah.pienote.core.navigation.PienoteScreens

@Composable
actual fun NoteScreen(
    state: NoteViewState,
    parentScreen: String?,
    onImageSelected: (Uri?) -> Unit,
    onUpdateCategory: (Long?) -> Unit,
    onSwitchEditMode: (String, String) -> Unit,
    onUpdateColor: (String?) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: (note: String, title: String) -> Unit
) {

}