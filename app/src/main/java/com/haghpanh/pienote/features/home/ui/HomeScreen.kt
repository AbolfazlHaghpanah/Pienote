package com.haghpanh.pienote.features.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteScaffold
import com.haghpanh.pienote.commonui.component.PienoteTopBar
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.navigation.AppScreens.NoteScreen
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.PienoteSnackbarHost
import com.haghpanh.pienote.commonui.utils.SnackbarManager
import com.haghpanh.pienote.features.home.ui.component.AddCategoryComponent
import com.haghpanh.pienote.features.home.ui.component.HomeCategoryItem
import com.haghpanh.pienote.features.home.ui.component.HomeNoteItem
import com.haghpanh.pienote.features.home.ui.component.MoveToCategoryComponent
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteBottomMenu
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteOptions
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteOptions.AddCategory
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteOptions.DeleteNotes
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteOptions.MoveToCategory

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
    val state by viewModel.collectAsStateWithLifecycle()
    val parent = viewModel.savedStateHandler<String>("parent")

    viewModel.HandleResult(
        prop = state::addToCategoryResult,
        onSuccess = { categoryId ->
            viewModel.snackbarManager.sendSuccess(
                "Moved To Category Category",
                action = {
                    navController.navigate(
                        route = AppScreens.CategoryScreen.createRoute(
                            categoryId,
                            HOME_SCREEN_NAME
                        )
                    )
                },
                actionLabel = "Show"
            )
        }
    )

    HomeScreen(
        state = state,
        parent = parent,
        snackbarManager = viewModel.snackbarManager,
        navigateToNote = { route ->
            navController.navigate(route = route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onDeleteNote = viewModel::deleteNote,
        onAddNewCategory = viewModel::addNewCategory,
        onAddNotesToCategory = viewModel::addNoteToCategory,
        navigateBack = {
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    parent: String?,
    snackbarManager: SnackbarManager,
    navigateToNote: (String) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onAddNewCategory: (List<Int>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Int>, categoryId: Int) -> Unit,
    navigateBack: () -> Unit
) {
    val listState = rememberLazyListState()
    val selectedNotes = remember { mutableStateListOf<Note>() }
    val shouldExpandFAB by remember {
        derivedStateOf {
            listState.canScrollForward.not()
        }
    }
    val isSelectingNote by remember {
        derivedStateOf { selectedNotes.isNotEmpty() }
    }

    // When clicking on each selected notes menu options this should set
    // the content of bottom menu set based on this value.
    var bottomMenuContentType: SelectingNoteOptions? by remember {
        mutableStateOf(null)
    }

    BackHandler {
        if (isSelectingNote) {
            selectedNotes.removeAll { true }
        } else {
            navigateBack()
        }
    }

    //we should sync notes that is available on screen with selected notes
    //so when we perform actions on selected notes that leads to remove some notes
    //from screen they should remove from selected list to.
    LaunchedEffect(state.notes) {
        selectedNotes.removeAll {
            state.notes?.contains(it) == false
        }
    }

    PienoteScaffold(
        snackbarHost = {
            PienoteSnackbarHost(manager = snackbarManager)
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isSelectingNote.not(),
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it * 2 }
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigateToNote(
                            NoteScreen.createRoute(
                                id = -1,
                                isExist = false,
                                parent = HOME_SCREEN_NAME
                            )
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Note"
                        )
                    },
                    text = {
                        Text(text = stringResource(R.string.label_add_note))
                    },
                    expanded = shouldExpandFAB
                )
            }
        },
        bottomMenu = {
            AnimatedVisibility(
                visible = isSelectingNote,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AnimatedContent(
                    targetState = bottomMenuContentType,
                    label = "",
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                ) { options ->
                    when (options) {
                        null -> {
                            SelectingNoteBottomMenu {
                                bottomMenuContentType = it
                            }
                        }

                        AddCategory -> {
                            AddCategoryComponent(
                                onAddNewCategory = { name, image ->
                                    onAddNewCategory(
                                        selectedNotes.map { note -> note.id },
                                        name,
                                        image
                                    )
                                },
                                onDiscard = { bottomMenuContentType = null }
                            )
                        }

                        MoveToCategory -> {
                            MoveToCategoryComponent(
                                onCategorySelected = { catId ->
                                    onAddNotesToCategory(
                                        selectedNotes.map { note -> note.id },
                                        catId
                                    )
                                },
                                categories = state.categories,
                                onDiscard = { bottomMenuContentType = null }
                            )
                        }

                        DeleteNotes -> {
                            // TODO implement This
                        }

                        else -> {}
                    }
                }
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
                if (parent != null) {
                    PienoteTopBar(
                        title = stringResource(R.string.label_home),
                        icon = R.drawable.home,
                        parent = parent,
                        onBack = navigateBack
                    )
                } else {
                    PienoteTopBar(
                        title = stringResource(R.string.label_home),
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
                val isNoteSelected by remember(selectedNotes.size) {
                    derivedStateOf { selectedNotes.contains(note) }
                }

                HomeNoteItem(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(PienoteTheme.shapes.veryLarge)
                        .animateItemPlacement(
                            animationSpec = tween(300)
                        ),
                    title = note.title,
                    note = note.note,
                    isSelected = isNoteSelected,
                    onDelete = {
                        onDeleteNote(note)
                    },
                    onClick = {
                        if (isSelectingNote) {
                            if (selectedNotes.contains(note)) {
                                selectedNotes.remove(note)
                            } else {
                                selectedNotes.add(note)
                            }
                        } else {
                            navigateToNote(
                                NoteScreen.createRoute(
                                    id = note.id,
                                    isExist = true,
                                    parent = HOME_SCREEN_NAME
                                )
                            )
                        }
                    },
                    onLongClick = {
                        selectedNotes.add(note)
                    }
                )
            }
        }
    }
}
