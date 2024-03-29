package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteScreen(
    navController: NavController
) {
    NoteScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {

}