package com.haghpanh.pienote.feature_noteslist.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NotesListScreen(
    navController: NavController
) {
    NotesListScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun NotesListScreen(
    navController: NavController,
    viewModel: NotesListViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    NotesListScreen(
        state = state,
        parentScreenName = viewModel.savedStateHandler<String>("parent"),
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun NotesListScreen(
    state: NotesListViewState,
    parentScreenName: String?,
    onBack: () -> Unit
) {
    Log.d("mmd", "NotesListScreen: ${state.notes.joinToString { it.title?:"null" }}")
}