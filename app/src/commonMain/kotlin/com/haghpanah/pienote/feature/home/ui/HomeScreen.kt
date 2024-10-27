package com.haghpanah.pienote.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haghpanah.pienote.coreui.component.PienoteScaffold
import com.haghpanah.pienote.coreui.component.PienoteTopBar
import com.haghpanah.pienote.coreui.model.NoteUiModel
import com.haghpanah.pienote.coreui.navigation.PienoteScreens
import com.haghpanah.pienote.coreui.theme.PienoteTheme
import com.haghpanah.pienote.feature.home.ui.component.HomeCategoryItem
import com.haghpanah.pienote.feature.home.ui.component.HomeNoteItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pienote.app.generated.resources.Res
import pienote.app.generated.resources.home
import pienote.app.generated.resources.label_add_note
import pienote.app.generated.resources.label_home

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreen(
        navController = navController,
        viewModel = HomeViewModel()
    )
}

@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()

    viewModel.handleEffectsDispose()

    HomeScreen(
        state = state,
//        snackbarManager = viewModel.snackbarManager,
        navigateToRoute = { route ->
            navController.navigate(route = route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onDeleteNote = { },
        onAddNewCategory = { _, _, _ -> },
        onAddNotesToCategory = { _, _ -> }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
//    snackbarManager: SnackbarManager,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteUiModel) -> Unit,
    onAddNewCategory: (List<Int>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Int>, categoryId: Int) -> Unit
) {
//    val context = LocalContext.current
    val listState = rememberLazyListState()
//    val selectedNotes = remember { mutableStateListOf<Note>() }
//    val shouldExpandFAB by remember {
//        derivedStateOf {
//            listState.canScrollForward.not()
//        }
//    }
//    val isSelectingNote by remember {
//        derivedStateOf { selectedNotes.isNotEmpty() }
//    }
//
//    // When clicking on each selected notes menu options this should set
//    // the content of bottom menu set based on this value.
//    var bottomMenuContentType: SelectingNoteOptions? by remember {
//        mutableStateOf(null)
//    }
//
//    BackHandler(isSelectingNote) {
//        selectedNotes.removeAll { true }
//    }
//
//    // we should sync notes that is available on screen with selected notes
//    // so when we perform actions on selected notes that leads to remove some notes
//    // from screen they should remove from selected list to.
//    LaunchedEffect(state.notes) {
//        selectedNotes.removeAll {
//            state.notes?.contains(it) == false
//        }
//    }
//
//    // if we don't do this after one time selecting notes and unselect them contentType
//    // is steel saved last state and if user select notes again bottom menu may show
//    // wrong content.
//    LaunchedEffect(isSelectingNote) {
//        if (!isSelectingNote) {
//            bottomMenuContentType = null
//        }
//    }
//
//    // shows snackbar for successfully move notes to a category
//    LaunchedEffect(state.movedToCategoryId) {
//        state.movedToCategoryId?.let { id ->
//            snackbarManager.sendSuccess(
//                "Moved To Category Category",
//                action = {
//                    navigateToRoute(
//                        AppScreens.CategoryScreen.createRoute(
//                            id,
//                            context.getString(R.string.label_home)
//                        )
//                    )
//                },
//                actionLabel = "Show"
//            )
//        }
//    }

    PienoteScaffold(
        snackbarHost = {
//            PienoteSnackbarHost(manager = snackbarManager)
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = false,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it * 2 }
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
//                        navigateToRoute(
//                            NoteScreen.createRoute(
//                                id = -1,
//                                isExist = false,
//                                parent = context.getString(R.string.label_home)
//                            )
//                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Note"
                        )
                    },
                    text = {
                        Text(text = stringResource(Res.string.label_add_note))
                    },
                    expanded = true
                )
            }
        }
    ) { paddingValue ->
        LazyColumn(
            contentPadding = paddingValue,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            state = listState
        ) {
            item {
//                PienoteTopBar(
//                    title = stringResource(Res.string.label_home),
//                    icon = Res.drawable.home,
//                )
            }

            items(
                items = state.categoriesChunked ?: emptyList(),
                key = { item -> item.first().id + (item.lastOrNull()?.id ?: 0) },
            ) { categoriesInARow ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .animateItemPlacement(
                            animationSpec = tween(300)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    categoriesInARow.forEach { category ->
                        HomeCategoryItem(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(
                                    if (categoriesInARow.size > 1) {
                                        0.8f
                                    } else {
                                        1.2f
                                    }
                                ),
                            name = category.name,
                            image = category.image,
                            noteCount = category.noteCounts
                        ) {
                            navigateToRoute(
                                PienoteScreens.CategoryScreen.createRoute(
                                    category.id,
                                    parent = "Home"
                                )
                            )
                        }
                    }
                }
            }

            items(
                items = state.notes ?: emptyList(),
                key = { item -> "${item.id}_${item.title}" }
            ) { note ->
//                val isNoteSelected by remember(selectedNotes.size) {
//                    derivedStateOf { selectedNotes.contains(note) }
//                }

                HomeNoteItem(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(PienoteTheme.shapes.veryLarge)
                        .animateItemPlacement(
                            animationSpec = tween(300)
                        ),
                    title = note.title,
                    note = note.markdown,
                    color = note.color,
                    isSelected = false,
                    onDelete = {
                        onDeleteNote(note)
                    },
                    onClick = {
//                        if (isSelectingNote) {
//                            if (selectedNotes.contains(note)) {
//                                selectedNotes.remove(note)
//                            } else {
//                                selectedNotes.add(note)
//                            }
//                        } else {
//                            navigateToRoute(
//                                NoteScreen.createRoute(
//                                    id = note.id,
//                                    isExist = true,
//                                    parent = context.getString(R.string.label_home)
//                                )
//                            )
//                        }
                    },
                    onLongClick = {
//                        selectedNotes.add(note)
                    }
                )
            }
        }
    }
}
