package com.haghpanh.pienote.features.home.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteTopBar
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.navigation.AppScreens.NoteScreen
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.features.home.ui.component.HomeCategoryItem
import com.haghpanh.pienote.features.home.ui.component.HomeNoteItem

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
                            ),
                        title = note.title,
                        note = note.note,
                        onDelete = {
                            onDeleteNote(note)
                        },
                        onClick = {
                            navigateToNote(
                                NoteScreen.createRoute(
                                    id = note.id,
                                    isExist = true,
                                    parent = HOME_SCREEN_NAME
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
