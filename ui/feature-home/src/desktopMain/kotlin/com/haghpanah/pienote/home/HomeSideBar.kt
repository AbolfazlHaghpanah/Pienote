package com.haghpanah.pienote.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eygraber.uri.Uri
import com.haghpanah.pienote.designsystem.component.PienoteChip
import com.haghpanah.pienote.designsystem.component.PienoteScaffold
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.home.component.AddCategoryComponent
import com.haghpanah.pienote.home.component.HomeCategoryItem
import com.haghpanah.pienote.home.component.HomeNoteItem
import com.haghpanah.pienote.home.component.HomeShowingItem
import com.haghpanah.pienote.home.component.MoveToCategoryComponent
import com.haghpanah.pienote.home.component.SelectingNoteBottomMenu
import com.haghpanah.pienote.home.component.SelectingNoteOptions
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.navigation.PienoteScreens
import com.haghpanah.pienote.shortcuthandler.addKeyboardShortcut
import com.haghpanah.pienote.snackbar.PienoteSnackbarHost
import com.haghpanah.pienote.snackbar.SnackbarManager
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeSideBar(
    navController: NavController,
    onChangeVisibility: (Boolean) -> Unit,
) {
    HomeSideBar(
        navController = navController,
        viewModel = koinViewModel(),
        onChangeVisibility = onChangeVisibility
    )
}

@Composable
private fun HomeSideBar(
    navController: NavController,
    viewModel: HomeViewModel,
    onChangeVisibility: (Boolean) -> Unit,
) {
    val state by viewModel.collectAsStateWithLifecycle()

    viewModel.handleEffectsDispose()

    HomeSideBar(
        state = state,
        onChangeVisibility = onChangeVisibility,
        navigateToRoute = { route ->
            navController.navigate(route = route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = false
            }
        },
        onDeleteNote = viewModel::deleteNote,
        onAddNewCategory = viewModel::addNewCategory,
        onAddNotesToCategory = viewModel::addNoteToCategory,
        snackbarManager = viewModel.snackbarManager
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeSideBar(
    state: HomeViewState,
    onChangeVisibility: (Boolean) -> Unit,
    navigateToRoute: (PienoteScreens) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, Uri?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit,
    snackbarManager: SnackbarManager,
) {
    val selectedNotes = remember { mutableStateListOf<NoteDomainModel>() }
    val isSelectingNote by remember {
        derivedStateOf { selectedNotes.isNotEmpty() }
    }
    var showingItem: HomeShowingItem? by remember {
        mutableStateOf(null)
    }
    val contentWidth by animateDpAsState(if (isSelectingNote) 500.dp else 300.dp)


    // When clicking on each selected notes menu options this should set
    // the content of bottom menu set based on this value.
    var bottomMenuContentType: SelectingNoteOptions? by remember {
        mutableStateOf(null)
    }

    // we should sync notes that is available on screen with selected notes
    // so when we perform actions on selected notes that leads to remove some notes
    // from screen they should remove from selected list to.
    LaunchedEffect(state.notes) {
        selectedNotes.removeAll {
            state.notes?.contains(it) == false
        }
    }

    // if we don't do this after one time selecting notes and unselect them contentType
    // is steel saved last state and if user select notes again bottom menu may show
    // wrong content.
    LaunchedEffect(isSelectingNote) {
        if (!isSelectingNote) {
            bottomMenuContentType = null
        }
    }

    PienoteScaffold(
        snackbarHost = {
            PienoteSnackbarHost(snackbarManager)
        },
        modifier = Modifier.widthIn(max = contentWidth),
        bottomMenu = {
            AnimatedVisibility(
                modifier = Modifier.padding(24.dp),
                visible = isSelectingNote,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it * 2 })
            ) {
                addKeyboardShortcut(Key.Escape) {
                    selectedNotes.removeAll { true }
                    true
                }

                AnimatedContent(
                    targetState = bottomMenuContentType,
                    label = "bottom menu options",
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                ) { options ->
                    when (options) {
                        null -> {
                            SelectingNoteBottomMenu {
                                bottomMenuContentType = it
                            }
                        }

                        SelectingNoteOptions.AddCategory -> {
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

                        SelectingNoteOptions.MoveToCategory -> {
                            MoveToCategoryComponent(
                                onCategorySelected = { catId ->
                                    onAddNotesToCategory(
                                        selectedNotes.map { note -> note.id },
                                        catId
                                    )
                                },
                                categories = state.categoriesChunked,
                                onDiscard = { bottomMenuContentType = null }
                            )
                        }

                        SelectingNoteOptions.DeleteNotes -> {
                            // TODO implement This
                        }

                        else -> {}
                    }
                }
            }
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = PienoteTheme.colors.onBackground
                    ),
                    title = {
                        Text(
                            text = "Pienote",
                            style = PienoteTheme.typography.headlineMedium,
                            color = PienoteTheme.colors.onBackground
                        )
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PienoteChip(
                                backgroundColor = Color.Transparent,
                                onClick = {
                                    navigateToRoute(
                                        PienoteScreens.NoteScreen(
                                            id = -1,
                                            isExist = false,
                                            parent = "Home",
                                        )
                                    )
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(4.dp),
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = null,
                                        tint = PienoteTheme.colors.onBackground
                                    )
                                }
                            )

                            PienoteChip(
                                backgroundColor = Color.Transparent,
                                onClick = {
                                    onChangeVisibility(false)
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.padding(4.dp),
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = null,
                                        tint = PienoteTheme.colors.onBackground
                                    )
                                }
                            )
                        }
                    },
                    scrollBehavior = null,
                )
            }

            items(
                items = state.categoriesChunked?.flatten() ?: emptyList(),
                key = { item -> item.id.hashCode() }
            ) { category ->
                Modifier.padding(horizontal = 24.dp)

                HomeCategoryItem(
                    modifier = Modifier.fillMaxWidth().aspectRatio(3.14f),
                    name = category.name,
                    image = category.image,
                    isShowing = showingItem?.isEqualToCategory(category.id) ?: false,
                    noteCount = category.noteCount
                ) {
                    if (showingItem?.isEqualToCategory(category.id) != true) {
                        showingItem = HomeShowingItem(
                            isNote = false,
                            id = category.id
                        )
                        navigateToRoute(
                            PienoteScreens.CategoryScreen(
                                category.id,
                                parent = "Home"
                            )
                        )
                    }
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
                        .fillMaxWidth()
                        .aspectRatio(2.4f)
                        .animateItem(
                            fadeInSpec = tween(),
                            fadeOutSpec = tween(),
                            placementSpec = spring()
                        ),
                    title = note.title,
                    note = note.markdown,
                    color = note.color,
                    isSelected = isNoteSelected,
                    onClick = {
                        if (isSelectingNote) {
                            if (selectedNotes.contains(note)) {
                                selectedNotes.remove(note)
                            } else {
                                selectedNotes.add(note)
                            }
                        } else {
                            if (showingItem?.isEqualToNote(note.id) != true) {
                                showingItem = HomeShowingItem(
                                    isNote = true,
                                    id = note.id
                                )
                                navigateToRoute(
                                    PienoteScreens.NoteScreen(
                                        id = note.id,
                                        isExist = true,
                                        parent = "Home"
                                    )
                                )
                            }
                        }
                    },
                    onLongClick = {
                        selectedNotes.add(note)
                    },
                    isShowing = showingItem?.isEqualToNote(note.id) ?: false
                )
            }
        }
    }
}
