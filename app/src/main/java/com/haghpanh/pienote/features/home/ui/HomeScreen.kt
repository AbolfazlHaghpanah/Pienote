package com.haghpanh.pienote.features.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
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
import com.haghpanh.pienote.features.home.ui.component.SelectBottomSheetOptions
import com.haghpanh.pienote.features.home.ui.component.SelectNoteSheetContent
import kotlinx.coroutines.launch

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
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeViewState,
    parent: String?,
    navigateToNote: (String) -> Unit,
    onDeleteNote: (Note) -> Unit,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val selectedNotes = remember { mutableStateListOf<Note>() }
    var bottomSheetTargetValue by remember { mutableStateOf(SheetValue.Hidden) }
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            confirmValueChange = {
                bottomSheetTargetValue = it
                true
            },
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )

    val shouldExpandFAB by remember {
        derivedStateOf {
            listState.canScrollForward.not()
        }
    }
    val isSelectingNote by remember {
        derivedStateOf { selectedNotes.isNotEmpty() }
    }

    // When clicking on each selected notes bottom sheet options this should set
    // the content of expanded bottom sheet set based on this value.
    var expandedBottomSheetContentType: SelectBottomSheetOptions? by remember {
        mutableStateOf(null)
    }

    BackHandler {
        if (isSelectingNote) {
            selectedNotes.removeAll { true }
        } else {
            navigateBack()
        }
    }

    // This LaunchedEffect makes sure that bottom sheet is expanded when clicked
    // on any selected notes options.
    LaunchedEffect(expandedBottomSheetContentType) {
        if (expandedBottomSheetContentType != null) {
            bottomSheetState.bottomSheetState.expand()
        }
    }

    // Because we are expanding bottom sheet based on expandedBottomSheetContentType
    // value being null or not we should make this null when bottom sheet is closed
    // so it can open again as we closed it.
    LaunchedEffect(bottomSheetTargetValue) {
        if (bottomSheetTargetValue != SheetValue.Expanded) {
            expandedBottomSheetContentType = null
        }
    }

    // Decide for bottom sheet to be hide or partialExpand based on selectedNotes list.
    LaunchedEffect(isSelectingNote) {
        if (isSelectingNote) {
            bottomSheetState.bottomSheetState.partialExpand()
        } else {
            bottomSheetState.bottomSheetState.hide()
        }
    }

    Scaffold(
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
    ) { paddingValues ->
        SelectNoteSheetContent(
            modifier = Modifier.padding(paddingValues),
            state = bottomSheetState,
            shouldDisableContentGesture = bottomSheetTargetValue == SheetValue.Expanded,
            partiallyExpandedContent = {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(SelectBottomSheetOptions.entries) { optionItem ->
                        AssistChip(
                            onClick = {
                                scope.launch {
                                    expandedBottomSheetContentType = optionItem
                                }
                            },
                            label = {
                                Text(text = optionItem.label)
                            },
                            shape = PienoteTheme.shapes.rounded
                        )
                    }
                }
            },
            expandedContent = {
                AnimatedContent(
                    targetState = expandedBottomSheetContentType,
                    label = "switch between bottom sheets options"
                ) {
                    when (it) {
                        SelectBottomSheetOptions.MoveToCategory -> {
                            Column(
                                Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Select Category",
                                    style = PienoteTheme.typography.headlineMedium,
                                )

                                HorizontalDivider()

                                state.categories?.forEach {
                                    Text(
                                        text = it.name,
                                        style = PienoteTheme.typography.titleMedium
                                    )
                                }
                            }
                        }

                        SelectBottomSheetOptions.AddToFavorite -> {
                        }

                        SelectBottomSheetOptions.DeleteNotes -> {
                        }

                        else -> {}
                    }
                }
            }
        ) { bottomSheetPaddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = bottomSheetPaddingValues,
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
}
