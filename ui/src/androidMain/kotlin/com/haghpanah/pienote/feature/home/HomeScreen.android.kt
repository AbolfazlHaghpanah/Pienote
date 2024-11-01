package com.haghpanah.pienote.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.core.component.PienoteScaffold
import com.haghpanah.pienote.core.component.PienoteTopBar
import com.haghpanah.pienote.core.navigation.PienoteScreens
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.feature.home.component.AddCategoryComponent
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.feature.home.component.HomeCategoryItem
import com.haghpanah.pienote.feature.home.component.HomeNoteItem
import com.haghpanah.pienote.feature.home.component.MoveToCategoryComponent
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteBottomMenu
import com.haghpanh.pienote.features.home.ui.component.SelectingNoteOptions
import org.jetbrains.compose.resources.stringResource
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.home
import pienote.ui.generated.resources.label_add_note
import pienote.ui.generated.resources.label_home

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal actual fun HomeScreen(
    state: HomeViewState,
    navigateToRoute: (String) -> Unit,
    onDeleteNote: (NoteDomainModel) -> Unit,
    onAddNewCategory: (List<Long>, String, String?) -> Unit,
    onAddNotesToCategory: (noteIds: List<Long>, categoryId: Long) -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val selectedNotes = remember { mutableStateListOf<NoteDomainModel>() }
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

    BackHandler(isSelectingNote) {
        selectedNotes.removeAll { true }
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

    // shows snackbar for successfully move notes to a category
    LaunchedEffect(state.movedToCategoryId) {
        state.movedToCategoryId?.let { id ->
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
        }
    }

    PienoteScaffold(
//        snackbarHost = {
//            PienoteSnackbarHost(manager = snackbarManager)
//        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isSelectingNote.not(),
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it * 2 }
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigateToRoute(
                            PienoteScreens.NoteScreen.createRoute(
                                id = -1,
                                isExist = false,
                                parent = "Home"
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
                        Text(text = stringResource(Res.string.label_add_note))
                    },
                    expanded = shouldExpandFAB
                )
            }
        },
        bottomMenu = {
            AnimatedVisibility(
                modifier = Modifier.padding(24.dp),
                visible = isSelectingNote,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it * 2 })
            ) {
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
                                        image?.path
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
    ) { paddingValue ->
        LazyColumn(
            contentPadding = paddingValue,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            state = listState
        ) {
            item {
                PienoteTopBar(
                    title = stringResource(Res.string.label_home),
                    icon = Res.drawable.home,
                )
            }

            items(
                items = state.categoriesChunked ?: emptyList(),
                key = { item -> item.first().id + (item.lastOrNull()?.id ?: 0) }
            ) { categoriesInARow ->
                Modifier.padding(horizontal = 24.dp)
                Row(
                    modifier = Modifier
                        .animateItem(
                            //TODO
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = tween<IntOffset>(300)
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
                            noteCount = category.noteCount
                        ) {
                            navigateToRoute(
                                PienoteScreens.CategoryScreen.createRoute(
                                    category.id.toInt(),
                                    parent = "context.getString(Res.string.label_home)"
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
                    note = note.markdown,
                    color = note.color,
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
                            navigateToRoute(
                                PienoteScreens.NoteScreen.createRoute(
                                    id = note.id.toInt(),
                                    isExist = true,
                                    parent = "Home"
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