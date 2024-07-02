package com.haghpanh.pienote.features.noteslist.ui

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.haghpanh.pienote.features.home.ui.component.HomeNoteItem

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

    val sdsds = viewModel.gf.collectAsLazyPagingItems()
    LazyColumn {
        items(sdsds.itemCount) {
            HomeNoteItem(
                title = sdsds[it]?.genrNotes?.title ?: "",
                note = sdsds[it]?.spcfFavorite?.favoriteType ?: "",
                onDelete = { /*TODO*/ }
            ) {
            }
        }
    }
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
    Log.d("mmd", "NotesListScreen: ${state.notes.joinToString { it.title ?: "null" }}")
}
