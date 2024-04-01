package com.haghpanh.pienote.note.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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
        viewModel.updateNote()
        navController.popBackStack()
    }

    DisposableEffect(state) {
        onDispose {
            viewModel.updateNote()
        }
    }

    HomeScreen(
        state = state,
        onUpdateNote = viewModel::updateNoteText,
        onUpdateTitle = viewModel::updateTitleText,
        onRequestToPickImage = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: NoteViewState,
    onUpdateNote: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
    onRequestToPickImage: () -> Unit
) {
    val scrollState = rememberScrollState()
    val nestedScrollConnection = rememberNoteNestedScrollConnection()

    Scaffold(
        modifier = Modifier.imePadding()
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(scrollState)
        ) {
            ImageCoverSection(
                modifier = Modifier
                    .statusBarsPadding()
                    .offset { IntOffset(0, nestedScrollConnection.imageOffset) }
                    .scale(nestedScrollConnection.imageScale)
                    .alpha(nestedScrollConnection.imageAlpha),
                image = state.note?.image,
                onClick = onRequestToPickImage
            )

            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.note?.title.orEmpty(),
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

                state.category?.let { category ->
                    val chipContentColor = PienoteTheme.colors.onBackground.copy(alpha = 0.7f)

                    Chip(
                        modifier = Modifier.padding(start = 14.dp),
                        onClick = {
                            //TODO navigate to category
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = PienoteTheme.colors.background,
                            contentColor = chipContentColor
                        ),
                        border = BorderStroke(1.dp, chipContentColor)
                    ) {
                        Text(
                            text = category.name,
                            style = PienoteTheme.typography.subtitle2
                        )
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 250.dp),
                    value = state.note?.note.orEmpty(),
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

                Spacer(modifier = Modifier.heightIn(200.dp))
            }
        }
    }
}