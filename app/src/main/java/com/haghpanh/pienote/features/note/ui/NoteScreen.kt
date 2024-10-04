package com.haghpanh.pienote.features.note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteChip
import com.haghpanh.pienote.commonui.component.PienoteScaffold
import com.haghpanh.pienote.commonui.component.PienoteTextField
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.toComposeColor
import com.haghpanh.pienote.features.note.ui.component.CategoryChipSection
import com.haghpanh.pienote.features.note.ui.component.ImageCoverSection
import com.haghpanh.pienote.features.note.ui.component.NoteColorSection
import com.haghpanh.pienote.features.note.utils.rememberNoteNestedScrollConnection
import com.haghpanh.pienote.features.texteditor.compose.PienoteTextEditor
import com.haghpanh.pienote.features.texteditor.utils.rememberTextEditorValue

@Composable
fun NoteScreen(
    navController: NavController
) {
    NoteScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()
    val parentScreen = viewModel.savedStateHandler<String>("parent")
    val focusManager = LocalFocusManager.current

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateNoteImage
    )

    viewModel.handleEffectsDispose()

    LaunchedEffect(state.canNavigateBack) {
        if (state.canNavigateBack == true) {
            navController.navigateUp()
        }
    }

    LaunchedEffect(state.isEditing) {
        if (!state.isEditing) {
            focusManager.clearFocus()
        }
    }

    NoteScreen(
        state = state,
        parentScreen = parentScreen,
        onUpdateCategory = viewModel::updateCategory,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onSwitchEditMode = viewModel::switchEditMode,
        onUpdateColor = viewModel::updateNoteColor,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = viewModel::onNavigateBackRequest
    )
}

@Composable
private fun NoteScreen(
    state: NoteViewState,
    parentScreen: String?,
    onUpdateCategory: (Int?) -> Unit,
    onRequestToPickImage: () -> Unit,
    onSwitchEditMode: () -> Unit,
    onUpdateColor: (String?) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: (note: String, title: String) -> Unit
) {
    val localConfig = LocalConfiguration.current
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()

    val noteText = rememberTextEditorValue(initialMarkdown = state.note.note.orEmpty())
    var titleText by rememberSaveable { mutableStateOf(state.note.title.orEmpty()) }

    // updating note Ui when note has observed successfully from database
    LaunchedEffect(key1 = state.note.title, key2 = state.note.note) {
        state.note.title?.let {
            titleText = it
        }

        state.note.note?.let {
            noteText.updateMarkdown(it)
        }
    }

    BackHandler {
        onBack(
            titleText,
            noteText.await().markdown
        )
    }

    // changes fab color based on note color
    val noteColor: Color by animateColorAsState(
        targetValue = state.note.color?.toComposeColor()
            ?: PienoteTheme.colors.primaryContainer,
        label = "change note color"
    )

    LaunchedEffect(state.isEditing) {
        // we need to check if user switch to reading mode from edit mode or not.
        // in that case we should scroll to top of screen because it may
        // cause some messed up in image section's nested scroll states (alpha, scale,etc.).
        if (!state.isEditing) {
            nestedScrollConnection.reset()
            scrollState.animateScrollTo(
                value = 0,
                animationSpec = tween(300)
            )
        }
    }

    PienoteScaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSwitchEditMode() },
                containerColor = noteColor,
                contentColor = PienoteTheme.colors.surface
            ) {
                AnimatedContent(
                    targetState = state.isEditing,
                    label = "switch edit mode"
                ) { isEditing ->
                    if (isEditing) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        topBar = {
            if (state.note.color != null) {
                AnimatedVisibility(visible = !state.isEditing) {
                    Box(
                        modifier = Modifier
                            .background(state.note.color.toComposeColor())
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                }
            }
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .then(
                    if (!state.isEditing) {
                        Modifier.nestedScroll(nestedScrollConnection)
                    } else {
                        Modifier
                    }
                )
                .verticalScroll(scrollState)
        ) {
            NoteColorSection(
                selectedColor = state.note.color,
                onUpdateColor = onUpdateColor,
                isEditing = state.isEditing,
                color = noteColor
            )

            if (parentScreen != null) {
                AnimatedVisibility(visible = !state.isEditing) {
                    PienoteChip(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        onClick = {
                            onBack(
                                titleText,
                                noteText.await().markdown
                            )
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "back"
                            )

                            Text(
                                modifier = Modifier.padding(end = 4.dp),
                                text = parentScreen,
                                style = PienoteTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }

            ImageCoverSection(
                modifier = Modifier
                    .padding(top = 14.dp, start = 24.dp, end = 24.dp)
                    .then(
                        if (!state.isEditing) {
                            Modifier
                                .offset { IntOffset(0, nestedScrollConnection.imageOffset) }
                                .scale(nestedScrollConnection.imageScale)
                                .alpha(nestedScrollConnection.imageAlpha)
                        } else {
                            Modifier
                        }
                    ),
                isEditing = state.isEditing,
                image = state.note.image,
                onClick = onRequestToPickImage
            )

            Column(
                modifier = Modifier
                    .heightIn(min = localConfig.screenHeightDp.dp - 24.dp)
            ) {
                PienoteTextField(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused && !state.isEditing) {
                                onSwitchEditMode()
                            }
                        },
                    value = titleText,
                    onValueChange = { titleText = it },
                    placeHolderText = stringResource(R.string.label_untitled),
                    textStyle = PienoteTheme.typography.displaySmall
                )

                AnimatedVisibility(visible = state.isEditing || state.category != null) {
                    CategoryChipSection(
                        category = state.category,
                        isEditing = state.isEditing,
                        categories = state.categories,
                        onCategorySelect = onUpdateCategory,
                        onClickCategory = { categoryId ->
                            navigateToRoute(
                                AppScreens.CategoryScreen.createRoute(
                                    categoryId,
                                    state.note.title ?: ""
                                )
                            )
                        }
                    )
                }

                PienoteTextEditor(
                    modifier = Modifier
                        .imePadding()
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused && !state.isEditing) {
                                onSwitchEditMode()
                            }
                        },
                    value = noteText,
                    textFieldModifier = Modifier,
                    shouldShowEditingOptions = state.isEditing,
                )
            }
        }
    }
}
