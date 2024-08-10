package com.haghpanh.pienote.features.home.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteTextField
import com.haghpanh.pienote.commonui.component.PienoteTopBar
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.navigation.AppScreens.NoteScreen
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.PienoteSnackbarHost
import com.haghpanh.pienote.commonui.utils.SnackbarManager
import com.haghpanh.pienote.features.home.ui.component.HomeCategoryItem
import com.haghpanh.pienote.features.home.ui.component.HomeNoteItem
import com.haghpanh.pienote.features.home.ui.component.SelectBottomSheetOptions
import com.haghpanh.pienote.features.home.ui.component.SelectBottomSheetOptions.AddCategory
import com.haghpanh.pienote.features.home.ui.component.SelectBottomSheetOptions.DeleteNotes
import com.haghpanh.pienote.features.home.ui.component.SelectBottomSheetOptions.MoveToCategory
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
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val selectedNotes = remember { mutableStateListOf<Note>() }
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            confirmValueChange = { true },
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

    LaunchedEffect(state.notes) {
        selectedNotes.removeAll {
            state.notes?.contains(it) == false
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
    LaunchedEffect(bottomSheetState.bottomSheetState.targetValue) {
        if (bottomSheetState.bottomSheetState.targetValue != SheetValue.Expanded) {
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

    LaunchedEffect(
        isSelectingNote,
        expandedBottomSheetContentType,
        selectedNotes,
        bottomSheetState.bottomSheetState
    ) {
        Log.d(
            "mmd",
            "---------------------${System.currentTimeMillis()} --------------------------"
        )
        Log.d("mmd", "is Selecting Note -> $isSelectingNote")
        Log.d("mmd", "selected Notes -> ${selectedNotes.joinToString { it.title }}")
        Log.d("mmd", "expanded Type -> ${expandedBottomSheetContentType?.label ?: "nulll"}")
        Log.d(
            "mmd",
            "bottom sheet target value -> ${bottomSheetState.bottomSheetState.targetValue}"
        )
    }

    Scaffold(
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
    ) { paddingValues ->
        SelectNoteSheetContent(
            modifier = Modifier,
            state = bottomSheetState,
            shouldDisableContentGesture = bottomSheetState.bottomSheetState.targetValue == SheetValue.Expanded,
            partiallyExpandedContent = {
                if (bottomSheetState.bottomSheetState.targetValue != SheetValue.Hidden) {
                    LazyRow(
                        modifier = if (
                            expandedBottomSheetContentType == AddCategory
                        ) {
                            Modifier.statusBarsPadding()
                        } else {
                            Modifier
                        },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            SelectBottomSheetOptions
                                .entries
                                .filter { options -> options.shouldShowInList }
                        ) { optionItem ->
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
                }
            },
            expandedContent = {
                AnimatedContent(
                    targetState = expandedBottomSheetContentType,
                    label = "switch between bottom sheets options"
                ) {
                    when (it) {
                        MoveToCategory -> {
                            BackHandler {
                                scope.launch {
                                    bottomSheetState.bottomSheetState.partialExpand()
                                }
                            }

                            Column(
                                Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = MoveToCategory.label,
                                    style = PienoteTheme.typography.headlineMedium,
                                )

                                HorizontalDivider()

                                state.categories?.forEach { cat ->
                                    Text(
                                        modifier = Modifier.clickable {
                                            onAddNotesToCategory(
                                                selectedNotes.map { note -> note.id },
                                                cat.id
                                            )
                                        },
                                        text = cat.name,
                                        style = PienoteTheme.typography.titleMedium
                                    )
                                }

                                Button(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    onClick = {
                                        expandedBottomSheetContentType =
                                            AddCategory
                                    }
                                ) {
                                    Text(text = "New Category")
                                }
                            }
                        }

                        DeleteNotes -> {
                            // TODO Implement Multiple note delete at a time
                        }

                        AddCategory -> {
                            BackHandler {
                                expandedBottomSheetContentType = MoveToCategory
                            }

                            var categoryName by remember {
                                mutableStateOf("")
                            }
                            var categoryImage: Uri? by remember {
                                mutableStateOf(null)
                            }

                            val pickMedia = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.PickVisualMedia(),
                                onResult = { uri -> categoryImage = uri }
                            )

                            Column(
                                Modifier
                                    .padding(horizontal = 24.dp)
                                    .imePadding()
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = AddCategory.label,
                                    style = PienoteTheme.typography.titleMedium,
                                )

                                HorizontalDivider()

                                AnimatedContent(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    targetState = categoryImage
                                ) { imageUri ->
                                    if (imageUri == null) {
                                        TextButton(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            onClick = {
                                                pickMedia.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            }
                                        ) {
                                            Text(text = stringResource(id = R.string.add_cover_image))
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .clickable {
                                                    pickMedia.launch(
                                                        PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                }
                                                .clip(PienoteTheme.shapes.small)
                                                .fillMaxWidth()
                                                .aspectRatio(1f),
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier
                                                    .clickable {
                                                        pickMedia.launch(
                                                            PickVisualMediaRequest(
                                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                                            )
                                                        )
                                                    }
                                                    .fillMaxWidth()
                                                    .aspectRatio(1f),
                                                model = categoryImage,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        Brush.verticalGradient(
                                                            listOf(
                                                                Color.Transparent,
                                                                Color.Transparent,
                                                                PienoteTheme.colors.surfaceContainerLowest
                                                            )
                                                        )
                                                    )
                                                    .fillMaxWidth()
                                                    .aspectRatio(1f),
                                            )
                                        }
                                    }
                                }

                                PienoteTextField(
                                    value = categoryName,
                                    onValueChange = { value -> categoryName = value },
                                    placeHolder = {
                                        Text(
                                            text = "Unnamed",
                                            style = PienoteTheme.typography.headlineSmall
                                        )
                                    },
                                    textStyle = PienoteTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            onAddNewCategory(
                                                selectedNotes.map { it.id },
                                                categoryName,
                                                categoryImage.toString()
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PienoteTheme.colors.tertiaryContainer,
                                            contentColor = PienoteTheme.colors.onTertiaryContainer
                                        )
                                    ) {
                                        Text(text = "Add")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            expandedBottomSheetContentType = MoveToCategory
                                        },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = PienoteTheme.colors.onErrorContainer
                                        ),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = PienoteTheme.colors.onErrorContainer
                                        )
                                    ) {
                                        Text(text = "Discard")
                                    }
                                }
                            }
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
