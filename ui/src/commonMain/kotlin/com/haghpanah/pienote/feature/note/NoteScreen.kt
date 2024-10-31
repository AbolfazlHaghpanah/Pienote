package com.haghpanah.pienote.feature.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.eygraber.uri.Uri
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteScreen(
    navController: NavController
) {
    NoteScreen(
        navController = navController,
        viewModel = koinViewModel()
    )
}

@Composable
private fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()
    val parentScreen = navController.currentBackStackEntry?.arguments?.getString("parent")
    val focusManager = LocalFocusManager.current

    viewModel.handleEffectsDispose()

    LaunchedEffect(state.canNavigateBack) {
        if (state.canNavigateBack == true) {
            navController.navigateUp()
        }
    }

    LaunchedEffect(state.isEditing) {
        if (!state.isEditing) {
            focusManager.clearFocus()
        }
    }

    NoteScreen(
        state = state,
        parentScreen = parentScreen,
        onUpdateCategory = viewModel::updateCategory,
        onImageSelected = viewModel::updateNoteImage,
        onSwitchEditMode = viewModel::switchEditMode,
        onUpdateColor = viewModel::updateNoteColor,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = viewModel::onNavigateBackRequest
    )
}

@Composable
internal expect fun NoteScreen(
    state: NoteViewState,
    parentScreen: String?,
    onImageSelected: (Uri?) -> Unit,
    onUpdateCategory: (Long?) -> Unit,
    onSwitchEditMode: (String, String) -> Unit,
    onUpdateColor: (String?) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: (note: String, title: String) -> Unit
)