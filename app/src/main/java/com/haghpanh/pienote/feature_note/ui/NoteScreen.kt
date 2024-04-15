package com.haghpanh.pienote.feature_note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.haghpanh.pienote.common_ui.navigation.AppScreens
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_note.ui.component.CategoryChipSection
import com.haghpanh.pienote.feature_note.ui.component.ImageCoverSection
import com.haghpanh.pienote.feature_note.utils.FocusRequestType
import com.haghpanh.pienote.feature_note.utils.rememberNoteNestedScrollConnection

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

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateNoteImage
    )

    BackHandler {
        if (state.isEditing && !state.isEmptyNote) {
            viewModel.switchEditMode()
        } else if (!state.isEmptyNote) {
            viewModel.updateOrInsertNote()
            navController.popBackStack()
        } else {
            navController.popBackStack()
        }
    }

    NoteScreen(
        state = state,
        onUpdateNote = viewModel::updateNoteText,
        onUpdateTitle = viewModel::updateTitleText,
        onUpdateCategory = viewModel::updateCategory,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onSwitchEditMode = viewModel::switchEditMode,
        navigateToRoute = { route -> navController.navigate(route) }
    )
}

@Composable
fun NoteScreen(
    state: NoteViewState,
    onUpdateNote: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
    onUpdateCategory: (Int?) -> Unit,
    onRequestToPickImage: () -> Unit,
    onSwitchEditMode: (FocusRequestType) -> Unit,
    navigateToRoute: (String) -> Unit
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
        }
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
            ImageCoverSection(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
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
                    SideEffect {
                        if (state.focusRequestType is FocusRequestType.Title) {
                            titleFocusRequester.requestFocus()
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .fillMaxWidth()
                            .focusRequester(titleFocusRequester),
                        value = state.note.title.orEmpty(),
                        onValueChange = onUpdateTitle,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.label_untitled),
                                style = PienoteTheme.typography.h1
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = PienoteTheme.typography.h1
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
                        text = state.note.title ?: stringResource(R.string.label_untitled),
                        style = PienoteTheme.typography.h1
                    )
                }

                CategoryChipSection(
                    category = state.category,
                    isEditing = state.isEditing,
                    categories = state.categories,
                    onCategorySelect = onUpdateCategory,
                    onClickCategory = {
                        navigateToRoute(AppScreens.CategoryScreen.createRoute(it))
                    }
                )

                if (state.isEditing) {
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
                            .focusRequester(noteFocusRequester),
                        value = state.note.note.orEmpty(),
                        onValueChange = onUpdateNote,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.label_write_here),
                                style = PienoteTheme.typography.body2
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = PienoteTheme.typography.body1
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
                        style = PienoteTheme.typography.body1
                    )
                }
            }
        }
    }
}