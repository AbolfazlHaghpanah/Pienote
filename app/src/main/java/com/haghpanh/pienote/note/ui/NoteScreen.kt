package com.haghpanh.pienote.note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanh.pienote.R
import com.haghpanh.pienote.baseui.theme.PienoteTheme
import com.haghpanh.pienote.note.ui.component.CategoryChipSection
import com.haghpanh.pienote.note.ui.component.ImageCoverSection
import com.haghpanh.pienote.note.utils.rememberNoteNestedScrollConnection

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
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateNoteImage
    )

    BackHandler {
        if (state.isEditing) {
            viewModel.switchEditMode()
        } else {
            viewModel.navigateBack()
            navController.popBackStack()
        }
    }

    HomeScreen(
        state = state,
        onUpdateNote = viewModel::updateNoteText,
        onUpdateTitle = viewModel::updateTitleText,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onSwitchEditMode = viewModel::switchEditMode
    )
}

@Composable
fun HomeScreen(
    state: NoteViewState,
    onUpdateNote: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
    onRequestToPickImage: () -> Unit,
    onSwitchEditMode: (Boolean?) -> Unit
) {
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()
    val titleFocusRequester = remember { FocusRequester() }
    val noteFocusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.imePadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSwitchEditMode(null) }
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
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
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
                    .statusBarsPadding()
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

            Column {
                if (state.isEditing) {
                    LaunchedEffect(state.shouldRequestFocusForTitle) {
                        if (state.shouldRequestFocusForTitle) {
                            titleFocusRequester.requestFocus()
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
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
                                onSwitchEditMode(true)
                            }
                            .padding(16.dp),
                        text = state.note.title ?: stringResource(R.string.label_untitled),
                        style = PienoteTheme.typography.h1
                    )
                }

                CategoryChipSection(
                    category = state.category,
                    isEditing = state.isEditing
                )

                if (state.isEditing) {
                    LaunchedEffect(state.shouldRequestFocusForNote) {
                        if (state.shouldRequestFocusForNote) {
                            noteFocusRequester.requestFocus()
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 250.dp)
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
                                onSwitchEditMode(false)
                            }
                            .padding(16.dp),
                        text = state.note.note ?: "",
                        style = PienoteTheme.typography.body1
                    )
                }

                Spacer(modifier = Modifier.heightIn(200.dp))
            }
        }
    }
}