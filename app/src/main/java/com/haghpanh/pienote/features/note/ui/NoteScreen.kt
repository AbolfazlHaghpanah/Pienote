package com.haghpanh.pienote.features.note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.haghpanh.pienote.commonui.navigation.AppScreens
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.features.note.ui.component.CategoryChipSection
import com.haghpanh.pienote.features.note.ui.component.ImageCoverSection
import com.haghpanh.pienote.features.note.utils.FocusRequestType
import com.haghpanh.pienote.features.note.utils.rememberNoteNestedScrollConnection

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
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel
) {
    val state by viewModel.collectAsStateWithLifecycle()
    val parentScreen = viewModel.savedStateHandler<String>("parent")

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateNoteImage
    )

    val onNavigateBack: () -> Unit = remember {
        {
            if (state.isEditing && !state.isEmptyNote) {
                viewModel.switchEditMode()
            } else if (!state.isEmptyNote) {
                viewModel.updateOrInsertNote()
                navController.popBackStack()
            } else {
                navController.popBackStack()
            }
        }
    }

    BackHandler(onBack = onNavigateBack)

    NoteScreen(
        state = state,
        parentScreen = parentScreen,
        onUpdateNote = viewModel::updateNoteText,
        onUpdateTitle = viewModel::updateTitleText,
        onUpdateCategory = viewModel::updateCategory,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onFocusRequestTypeChanged = viewModel::updateFocusRequester,
        onSwitchEditMode = viewModel::switchEditMode,
        navigateToRoute = { route -> navController.navigate(route) },
        onBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    state: NoteViewState,
    parentScreen: String?,
    onUpdateNote: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
    onUpdateCategory: (Int?) -> Unit,
    onRequestToPickImage: () -> Unit,
    onFocusRequestTypeChanged: (FocusRequestType) -> Unit,
    onSwitchEditMode: (FocusRequestType) -> Unit,
    navigateToRoute: (String) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()
    val titleFocusRequester = remember { FocusRequester() }
    val noteFocusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val localConfig = LocalConfiguration.current

    LaunchedEffect(state.isEditing) {
        if (!state.isEditing) {
            nestedScrollConnection.reset()
            scrollState.animateScrollTo(
                value = 0,
                animationSpec = tween(300)
            )
        }

        if (state.focusRequestType is FocusRequestType.Non) {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSwitchEditMode(FocusRequestType.Non) }
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
        contentWindowInsets = WindowInsets(0.dp)
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
            if (parentScreen != null) {
                AnimatedVisibility(visible = !state.isEditing) {
                    PienoteChip(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        onClick = onBack
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
                if (state.isEditing) {
                    var title by remember {
                        mutableStateOf(state.note.title)
                    }

                    DisposableEffect(title) {
                        onDispose {
                            title?.let(onUpdateTitle)
                        }
                    }

                    SideEffect {
                        if (state.focusRequestType is FocusRequestType.Title) {
                            titleFocusRequester.requestFocus()
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .fillMaxWidth()
                            .focusRequester(titleFocusRequester)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    onFocusRequestTypeChanged(FocusRequestType.Title)
                                }
                            },
                        value = title.orEmpty(),
                        onValueChange = { title = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.label_untitled),
                                style = PienoteTheme.typography.displaySmall
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = PienoteTheme.typography.displaySmall
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onSwitchEditMode(FocusRequestType.Title)
                            }
                            .padding(vertical = 16.dp, horizontal = 30.dp)
                            .fillMaxWidth(),
                        text = if (state.note.title.isNullOrEmpty()) {
                            stringResource(R.string.label_untitled)
                        } else {
                            state.note.title
                        },
                        style = PienoteTheme.typography.displaySmall
                    )
                }

                AnimatedVisibility(visible = state.isEditing || state.category != null) {
                    CategoryChipSection(
                        category = state.category,
                        isEditing = state.isEditing,
                        categories = state.categories,
                        onCategorySelect = onUpdateCategory,
                        onClickCategory = {
                            navigateToRoute(
                                AppScreens.CategoryScreen.createRoute(
                                    it,
                                    state.note.title ?: ""
                                )
                            )
                        }
                    )
                }

                if (state.isEditing) {
                    var note by remember {
                        mutableStateOf(state.note.note)
                    }

                    DisposableEffect(note) {
                        onDispose {
                            note?.let(onUpdateNote)
                        }
                    }

                    SideEffect {
                        if (state.focusRequestType is FocusRequestType.Note) {
                            noteFocusRequester.requestFocus()
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .focusRequester(noteFocusRequester)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    onFocusRequestTypeChanged(FocusRequestType.Note)
                                }
                            },
                        value = note.orEmpty(),
                        onValueChange = { note = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.label_write_here),
                                style = PienoteTheme.typography.bodyMedium
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = PienoteTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onSwitchEditMode(FocusRequestType.Note)
                            }
                            .padding(vertical = 16.dp, horizontal = 30.dp)
                            .fillMaxWidth(),
                        text = state.note.note ?: "",
                        style = PienoteTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
