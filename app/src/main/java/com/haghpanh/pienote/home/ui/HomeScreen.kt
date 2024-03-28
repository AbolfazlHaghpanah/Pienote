package com.haghpanh.pienote.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanh.pienote.home.ui.component.HomeNoteItem
import com.haghpanh.pienote.home.ui.component.QuickNoteButton
import com.haghpanh.pienote.home.ui.component.QuickNoteTextField

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onInsertQuickNote = viewModel::insertNotes,
        onUpdateQuickNoteNote = viewModel::setQuickNoteNote,
        onUpdateQuickNoteTitle = viewModel::setQuickNoteTitle,
        onQuickNoteClick = viewModel::reverseQuickNoteState,
        onQuickNoteDiscard = viewModel::reverseQuickNoteState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    onInsertQuickNote: () -> Unit,
    onUpdateQuickNoteTitle: (String) -> Unit,
    onUpdateQuickNoteNote: (String) -> Unit,
    onQuickNoteClick: () -> Unit,
    onQuickNoteDiscard: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    AnimatedContent(
                        targetState = state.hasClickedOnQuickNote,
                        label = "expanded quick note",
                        transitionSpec = {
                            fadeIn(tween(300))
                                .togetherWith(fadeOut(tween(300)))
                        }
                    ) { wantsToAdd ->
                        if (wantsToAdd) {
                            QuickNoteTextField(
                                title = state.quickNoteTitle.orEmpty(),
                                note = state.quickNoteNote.orEmpty(),
                                onDone = onInsertQuickNote,
                                onUpdateTitle = onUpdateQuickNoteTitle,
                                onUpdateNote = onUpdateQuickNoteNote,
                                onDiscard = onQuickNoteDiscard
                            )
                        } else {
                            QuickNoteButton {
                                onQuickNoteClick()
                            }
                        }
                    }
                }

                items(
                    items = state.notes ?: emptyList(),
                    key = { item -> item.id }
                ) { note ->
                    HomeNoteItem(
                        modifier = Modifier.animateItemPlacement(),
                        title = note.title,
                        note = note.note
                    )
                }
            }
        }
    }
}
