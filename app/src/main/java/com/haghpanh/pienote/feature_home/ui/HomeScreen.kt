package com.haghpanh.pienote.feature_home.ui

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.haghpanh.pienote.R
import com.haghpanh.pienote.common_ui.component.PienoteTopBar
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.navigation.AppScreens.NoteScreen
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_home.ui.component.HomeCategoryItem
import com.haghpanh.pienote.feature_home.ui.component.HomeNoteItem
import com.haghpanh.pienote.feature_home.ui.component.QuickNoteButton
import com.haghpanh.pienote.feature_home.ui.component.QuickNoteTextField

const val HOME_SCREEN_NAME = "Home"

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
    val parent = viewModel.savedStateHandle<String>("parent")

    HomeScreen(
        state = state,
        parent = parent,
        onInsertQuickNote = viewModel::insertNotes,
        onUpdateQuickNoteNote = viewModel::setQuickNoteNote,
        onUpdateQuickNoteTitle = viewModel::setQuickNoteTitle,
        onQuickNoteClick = viewModel::reverseQuickNoteState,
        onQuickNoteDiscard = viewModel::reverseQuickNoteState,
        navigateToNote = { route ->
            navController.navigate(route = route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onDeleteNote = viewModel::deleteNote,
        navigateBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    parent: String?,
    onInsertQuickNote: () -> Unit,
    onUpdateQuickNoteTitle: (String) -> Unit,
    onUpdateQuickNoteNote: (String) -> Unit,
    onQuickNoteClick: () -> Unit,
    onQuickNoteDiscard: () -> Unit,
    navigateToNote: (String) -> Unit,
    onDeleteNote: (Note) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .pointerInput(null) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, delta ->
                        if (delta > 0) {
                            navigateToNote(AppScreens.LibraryScreen.route)
                        }
                    }
                )
            },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigateToNote(
                    NoteScreen.createRoute(
                        id = -1,
                        isExist = false,
                        parent = HOME_SCREEN_NAME
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    if (parent != null) {
                        PienoteTopBar(
                            title = "Home",
                            icon = R.drawable.home,
                            parent = parent,
                            onBack = navigateBack
                        )
                    } else {
                        PienoteTopBar(
                            title = "Home",
                            icon = R.drawable.home,
                        )
                    }
                }

                item {
                    AnimatedContent(
                        targetState = state.hasClickedOnQuickNote,
                        label = "expanded quick note",
                        transitionSpec = {
                            fadeIn(tween(300))
                                .togetherWith(fadeOut(tween(300)))
                        }
                    ) { wantsToAddQuickNote ->
                        if (wantsToAddQuickNote) {
                            QuickNoteTextField(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                title = state.quickNoteTitle.orEmpty(),
                                note = state.quickNoteNote.orEmpty(),
                                onDone = onInsertQuickNote,
                                onUpdateTitle = onUpdateQuickNoteTitle,
                                onUpdateNote = onUpdateQuickNoteNote,
                                onDiscard = onQuickNoteDiscard
                            )
                        } else {
                            QuickNoteButton(
                                modifier = Modifier.padding(horizontal = 24.dp)
                            ) {
                                onQuickNoteClick()
                            }
                        }
                    }
                }

                items(
                    items = state.categories ?: emptyList(),
                    key = { item -> item.id }
                ) { category ->
                    HomeCategoryItem(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .animateItemPlacement(
                                animationSpec = tween(300)
                            ),
                        name = category.name,
                        priority = category.priority
                    ) {
                        navigateToNote(
                            AppScreens.CategoryScreen.createRoute(
                                category.id,
                                parent = HOME_SCREEN_NAME
                            )
                        )
                    }
                }

                items(
                    items = state.notes ?: emptyList(),
                    key = { item -> "${item.id}_${item.title}" }
                ) { note ->
                    HomeNoteItem(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .clip(PienoteTheme.shapes.veryLarge)
                            .animateItemPlacement(
                                animationSpec = tween(300)
                            )
                            .clickable {
                                navigateToNote(
                                    NoteScreen.createRoute(
                                        id = note.id,
                                        isExist = true,
                                        parent = HOME_SCREEN_NAME
                                    )
                                )
                            },
                        title = note.title,
                        note = note.note,
                        onDelete = {
                            onDeleteNote(note)
                        }
                    )
                }
            }
        }
    }
}
